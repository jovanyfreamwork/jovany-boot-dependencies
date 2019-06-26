package cn.jovanyframework.cloud.filestore.core;

//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFutureTask;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.jovany.command.CommandApi;
import cn.jovany.ffmpeg.M3U8;
import cn.jovany.ffmpeg.M3U8Builder.M3U8Opt;
import cn.jovany.ffmpeg.OpensslApi;
import cn.jovany.ffmpeg.Video2Chip;
import cn.jovany.ffmpeg.Video2EncryptedChip;
import cn.jovany.ffmpeg.signature.SignatureToken;

@Controller
@RequestMapping("store")
public class FileStoreController {

	private final class Video2EncryptedChipCallable implements Callable<M3U8Opt> {
		private final Path path;
		private final Video2EncryptedChip video2EncryptedChip;
		private final String secret;

		private Video2EncryptedChipCallable(Path path, Video2EncryptedChip video2EncryptedChip, String secret) {
			this.path = path;
			this.video2EncryptedChip = video2EncryptedChip;
			this.secret = secret;
		}

		@Override
		public M3U8Opt call() throws Exception {

			Map<String, Object> data = new LinkedHashMap<>();
			data.put("file", path.toString());
			data.put("directory", FileStoreController.this.downloadPath);

			SignatureToken token = video2EncryptedChip.buildSignatureTokenCallback(secret, 10000, data).call();

			M3U8Opt m3u8OptEncrypted = video2EncryptedChip.buildTaskCallback(secret, token.getSignature(), data).call();

			return m3u8OptEncrypted;
		}
	}

	private final class Video2ChipCallable implements Callable<M3U8Opt> {
		private final Path path;
		private final Video2Chip video2Chip;
		private final String secret;

		private Video2ChipCallable(Path path, Video2Chip video2Chip, String secret) {
			this.path = path;
			this.video2Chip = video2Chip;
			this.secret = secret;
		}

		@Override
		public M3U8Opt call() throws Exception {

			Map<String, Object> data = new LinkedHashMap<>();
			data.put("file", path.toString());
			data.put("directory", FileStoreController.this.downloadPath);

			SignatureToken token = video2Chip.buildSignatureTokenCallback(secret, 10000, data).call();

			M3U8Opt m3u8OptEncrypted = video2Chip.buildTaskCallback(secret, token.getSignature(), data).call();
			return m3u8OptEncrypted;
		}
	}

	@Value("${store.path.upload}")
	private String uploadPath;

	@Value("${store.path.download}")
	private String downloadPath;

	@Value("${store.file.download-uri}")
	private String downloadUri;

	@Autowired
	private CommandApi ffmpeg;

	@Autowired
	private OpensslApi opensslApi;

	/**
	 * 根据md5下载或者播放查询到的文件
	 * 
	 * @param md5
	 * @return
	 * @throws Exception
	 */
	@GetMapping("{md5}")
	public ResponseEntity<Resource> download(@PathVariable String md5) throws Exception {
		Path m3u8ToPath = Paths.get(downloadPath, md5, "out.enc.m3u8");
		if (Files.notExists(m3u8ToPath)) {
			m3u8ToPath = Paths.get(downloadPath, md5, "out.m3u8");
			if (Files.notExists(m3u8ToPath)) {
				return ResponseEntity.notFound().build();
			}
		}

		M3U8 m3u8 = new M3U8(m3u8ToPath);
//		m3u8.replaceKeyUri(linkTo(methodOn(FileStoreController.class).download("")).toString() + "${md5}");
		m3u8.replaceKeyUri(MessageFormat.format("{0}/{1}/{2}", downloadUri, md5, "${filename}"));
		m3u8.replaceChipUri(MessageFormat.format("{0}/{1}/{2}", downloadUri, md5, "${filename}"));
		Resource resource = new ByteArrayResource(m3u8.toString().getBytes());
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/x-mpegURL"))
				.header(HttpHeaders.CONTENT_DISPOSITION, MessageFormat.format("attachment; filename=\"{0}.m3u8\"", md5))
				.body(resource);
	}

	/**
	 * 上传文件至上传目录，并获取md5值，移动文件值下载目录，替换文件名为"md5.后缀名"
	 * 
	 * @param file
	 * @return md5
	 * @throws Exception
	 */
	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws Exception {
		String fileName = file.getOriginalFilename();
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		String fileNameRand = opensslApi.rand16();
		Path path = Paths.get(this.uploadPath, fileNameRand + suffixName);
		if (Files.notExists(path.getParent())) {
			Files.createDirectories(path.getParent());
		}
		file.transferTo(path.toFile());
		String md5 = DigestUtils.md5Hex(new FileInputStream(path.toFile()));
		Files.move(path, Paths.get(this.downloadPath, md5 + suffixName));
		return ResponseEntity.ok(md5);
	}

	/**
	 * 上传文件值上传目录，然后进行加密分片至下载目录，并返回md5
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/upload/encode")
	public ResponseEntity<?> uploadAndEncode(@RequestParam("file") MultipartFile file) throws Exception {
		String fileName = file.getOriginalFilename();
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		String fileNameRand = opensslApi.rand16();
		Path path = Paths.get(this.uploadPath, fileNameRand + suffixName);
		if (Files.notExists(path.getParent())) {
			Files.createDirectories(path.getParent());
		}
		file.transferTo(path.toFile());
		String md5 = DigestUtils.md5Hex(new FileInputStream(path.toFile()));

		if (!Arrays.asList(".mp4").contains(suffixName.toLowerCase())) {
			return ResponseEntity.ok(md5);
		}

		Video2EncryptedChip video2EncryptedChip = new Video2EncryptedChip(ffmpeg, opensslApi);
		String secret = opensslApi.rand16();
		ExecutorService es = Executors.newSingleThreadExecutor();
		ListenableFutureTask<M3U8Opt> result = new ListenableFutureTask<M3U8Opt>(
				new Video2EncryptedChipCallable(path, video2EncryptedChip, secret));
		es.submit(result);
		return ResponseEntity.ok(md5);
	}

	/**
	 * 上传文件值上传目录，然后进行(不加密)分片至下载目录，并返回md5
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/upload/decode")
	public ResponseEntity<?> uploadAndDecode(@RequestParam("file") MultipartFile file) throws Exception {
		String fileName = file.getOriginalFilename();
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		String fileNameRand = opensslApi.rand16();
		Path path = Paths.get(this.uploadPath, fileNameRand + suffixName);
		if (Files.notExists(path.getParent())) {
			Files.createDirectories(path.getParent());
		}
		file.transferTo(path.toFile());

		Video2Chip video2Chip = new Video2Chip(ffmpeg);
		String md5 = DigestUtils.md5Hex(new FileInputStream(path.toFile()));
		String secret = opensslApi.rand16();

		if (!Arrays.asList(".mp4").contains(suffixName.toLowerCase())) {
			return ResponseEntity.ok(md5);
		}

		ExecutorService es = Executors.newSingleThreadExecutor();
		ListenableFutureTask<M3U8Opt> result = new ListenableFutureTask<M3U8Opt>(
				new Video2ChipCallable(path, video2Chip, secret));
		es.submit(result);
		return ResponseEntity.ok(md5);
	}

}
