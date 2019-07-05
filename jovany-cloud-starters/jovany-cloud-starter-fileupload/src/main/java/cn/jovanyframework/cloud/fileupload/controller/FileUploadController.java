package cn.jovanyframework.cloud.fileupload.controller;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.jovany.ffmpeg.signature.SignatureToken;
import cn.jovanyframework.cloud.fileupload.core.FileUploadRequest;
import cn.jovanyframework.cloud.fileupload.core.FileUploadRequestData;
import cn.jovanyframework.cloud.fileupload.core.FileUploadRequestProvider;
import cn.jovanyframework.cloud.fileupload.core.PathSignatureProvider.PathSignatureContext;

//import cn.jovanyframework.cloud.fileupload.core.FileUploadRequest;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class FileUploadController {

	@Autowired
	private HttpServletRequest httpServletRequest;

	private final static String Default_Chunk_Size = new BigDecimal(1024 * 1024).toString();

	public String getDefaultChunkSize() {
		String chunkSize = httpServletRequest.getParameter("chunkSize");
		return chunkSize == null ? Default_Chunk_Size : chunkSize;
	}

	@Value("${store.path.upload}")
	private String uploadPath;

	public final class FileUploadChunkData implements FileUploadRequestData {
		private final int index;
		private final String hash;
		private final PathSignatureContext context;

		private FileUploadChunkData(PathSignatureContext context, String hash, int index) {
			this.index = index;
			this.context = context;
			this.hash = hash;
		}

		@Override
		public String getUploadPath() {
			return uploadPath;
		}

		@Override
		public String getSize() {
			long totalSize = new BigDecimal(context.fileUploadRequestValue(FileUploadRequest::getSize)).longValue();
			long chunkSize = new BigDecimal(context.fileUploadRequestValue(FileUploadRequest::getChunkSize))
					.longValue();
			long chunkCount = totalSize / chunkSize;
			long lastChunkSize = totalSize % chunkSize;
			long chunkTotal = chunkCount + (lastChunkSize > 0 ? 1 : 0);
			if (index >= chunkTotal) {
				throw new ArrayIndexOutOfBoundsException(
						MessageFormat.format("index({0}) >= total({1})", index, chunkTotal));
			}
			if (index == chunkCount && lastChunkSize > 0) {
				return String.valueOf(lastChunkSize);
			}
			return String.valueOf(chunkSize);
		}

		@Override
		public String getOriginalFilename() {
			return context.fileUploadRequestValue(FileUploadRequest::getOriginalFilename) + ".chunk" + index;
		}

		@Override
		public String getName() {
			return "chunk";
		}

		@Override
		public String getHash() {
			return hash;
		}

		@Override
		public String getChunkSize() {
			return context.fileUploadRequestValue(FileUploadRequest::getChunkSize);
		}

		@Override
		public String getContentType() {
			return "text/plain";
		}

		public int getIndex() {
			return index;
		}

	}

	@PutMapping("{requestId}")
	@CrossOrigin
	public ResponseEntity<String> uploadHead(//
			@PathVariable("requestId") String requestId, //
			@RequestParam("file") MultipartFile file //
	) throws Exception {
		FileUploadRequestProvider<?> provider = new FileUploadRequestProvider<PathSignatureContext>() {

			@Override
			public Callable<PathSignatureContext> callback(PathSignatureContext context) throws Exception {
				return () -> context;
			}

			@Override
			public void init(FileUploadRequest request) {
				request.init(new FileUploadRequestData() {

					@Override
					public String getUploadPath() {
						return uploadPath;
					}

					@Override
					public String getSize() {
						return String.valueOf(file.getSize());
					}

					@Override
					public String getOriginalFilename() {
						return file.getOriginalFilename();
					}

					@Override
					public String getName() {
						return file.getName();
					}

					@Override
					public String getHash() {
						return requestId;
					}

					@Override
					public String getChunkSize() {
						return new BigDecimal(1024 * 1024).toString();
					}

					@Override
					public String getContentType() {
						return file.getContentType();
					}

				});

			}

		};
		SignatureToken token = provider.getOrApply(requestId, 1000 * 60 * 60 * 24 * 7, new HashMap<>());
		FileUploadRequest request = new FileUploadRequest(token);
		return ResponseEntity.ok(request.tokenValue(SignatureToken::getSignature));
	}

	@PostMapping("{requestId}")
	public ResponseEntity<FileUploadChunks> upload(//
			@RequestParam("file") MultipartFile file, //
			@RequestParam("signature") String signature, //
			@PathVariable("requestId") String requestId //
	) throws Exception {
		FileUploadRequestProvider<FileUploadChunks> provider = new FileUploadRequestProvider<FileUploadChunks>() {

			@Override
			public Callable<FileUploadChunks> callback(PathSignatureContext context) throws Exception {
				return () -> {
					ArrayList<FileUploadRequestDataToken> result = new ArrayList<>();
					
					Path filePath = Paths.get(context.fileUploadRequestValue(FileUploadRequest::getUploadFile));
					if (Files.exists(filePath) && filePath.toFile().length() == file.getSize()) {
						context.removelock();
						return new FileUploadChunks(result, file);
					}
					
					long totalSize = new BigDecimal(context.fileUploadRequestValue(FileUploadRequest::getSize))
							.longValue();
					long chunkSize = new BigDecimal(context.fileUploadRequestValue(FileUploadRequest::getChunkSize))
							.longValue();
					long chunkCount = totalSize / chunkSize + (totalSize % chunkSize > 0 ? 1 : 0);


					for (int index = 0; index < chunkCount; index++) {
						FileUploadChunkData fileUploadChunkData = new FileUploadChunkData(context, requestId, index);
						Path chunkPath = Paths.get(fileUploadChunkData.getUploadFile());
						if (Files.exists(chunkPath) && chunkPath.toFile().length() == new BigDecimal(fileUploadChunkData.getSize())
								.longValue()) {
							continue;
						}
						FileUploadRequestProvider<?> chunkProvider = new FileUploadRequestProvider<Object>() {

							@Override
							public Callable<Object> callback(PathSignatureContext context) throws Exception {
								return null;
							}

							@Override
							public void init(FileUploadRequest request) {
								request.init(fileUploadChunkData);
							}

						};
						SignatureToken token = chunkProvider.apply(requestId, 1000 * 60 * 60 * 24 * 7, new HashMap<>());
						result.add(new FileUploadRequestDataToken(fileUploadChunkData, token));
					}
					context.removelock();
					return new FileUploadChunks(result, file);
				};
			}

			@Override
			public void init(FileUploadRequest request) {
				request.init(new FileUploadRequestData() {

					@Override
					public String getUploadPath() {
						return uploadPath;
					}

					@Override
					public String getSize() {
						return String.valueOf(file.getSize());
					}

					@Override
					public String getOriginalFilename() {
						return file.getOriginalFilename();
					}

					@Override
					public String getName() {
						return file.getName();
					}

					@Override
					public String getHash() {
						return requestId;
					}

					@Override
					public String getChunkSize() {
						return getDefaultChunkSize();
					}

					@Override
					public String getContentType() {
						return file.getContentType();
					}
				});
			}

		};
		Callable<FileUploadChunks> callable = provider.buildTaskCallback(requestId, signature, new HashMap<>());
		return ResponseEntity.ok().body(callable.call());
	}

	public class FileUploadRequestDataToken {
		private final FileUploadChunkData fileUploadRequestData;
		private final SignatureToken signatureToken;

		public FileUploadRequestDataToken(FileUploadChunkData fileUploadRequestData, SignatureToken signatureToken) {
			super();
			this.fileUploadRequestData = fileUploadRequestData;
			this.signatureToken = signatureToken;
		}

		public String getName() {
			return fileUploadRequestData.getName();
		}

		public String getFilename() {
			return fileUploadRequestData.getOriginalFilename();
		}

		public long getSize() {
			return new BigDecimal(fileUploadRequestData.getSize()).longValue();
		}

		public String getContextType() {
			return fileUploadRequestData.getContentType();
		}

		public String getSignature() {
			return signatureToken.getSignature();
		}

		public int getIndex() {
			return fileUploadRequestData.getIndex();
		}

		public long getOffset() {
			return getIndex() * new BigDecimal(fileUploadRequestData.getChunkSize()).longValue();
		}

	}

	public class FileUploadChunks {
		private final ArrayList<FileUploadRequestDataToken> chunks;
		private final long chunkTotal;

		public FileUploadChunks(ArrayList<FileUploadRequestDataToken> result, MultipartFile file) {
			super();
			this.chunks = result;
			long size = file.getSize();
			long chunkSize = getChunkSize();
			this.chunkTotal = size / chunkSize + (size % chunkSize > 0 ? 1 : 0);
		}

		public ArrayList<FileUploadRequestDataToken> getChunks() {
			return chunks;
		}

		public long getChunkSize() {
			return new BigDecimal(getDefaultChunkSize()).longValue();
		}

		public long getChunkTotal() {
			return chunkTotal;
		}

	}

	@PatchMapping("{requestId}/{index}")
	public ResponseEntity<Long> uploadChunk(//
			@RequestParam("chunk") MultipartFile chunk, //
			@RequestParam("signature") String signature, //
			@PathVariable("requestId") String requestId, //
			@PathVariable("index") int index) throws Exception {
		FileUploadRequestProvider<Long> provider = new FileUploadRequestProvider<Long>() {

			@Override
			public Callable<Long> callback(PathSignatureContext context) throws Exception {
				return () -> {
					chunk.transferTo(context.getPath());
					return context.destory(() -> context.getPath().toFile().length());
				};
			}

			@Override
			public void init(FileUploadRequest request) {
				request.init(new FileUploadRequestData() {

					@Override
					public String getUploadPath() {
						return uploadPath;
					}

					@Override
					public String getSize() {
						return String.valueOf(chunk.getSize());
					}

					@Override
					public String getOriginalFilename() {
						return chunk.getOriginalFilename();
					}

					@Override
					public String getName() {
						return chunk.getName();
					}

					@Override
					public String getHash() {
						return requestId;
					}

					@Override
					public String getChunkSize() {
						return getDefaultChunkSize();
					}

					@Override
					public String getContentType() {
						return chunk.getContentType();
					}
				});
			}
		};
		Callable<Long> callable = provider.buildTaskCallback(requestId, signature, new HashMap<>());
		return ResponseEntity.ok().body(callable.call());
	}

	@PatchMapping("{requestId}")
	public ResponseEntity<Long> uploadComplete(//
			@RequestParam("file") MultipartFile file, //
			@RequestParam("signature") String signature, //
			@PathVariable("requestId") String requestId //
	) throws Exception {
		FileUploadRequestProvider<Long> provider = new FileUploadRequestProvider<Long>() {

			@Override
			public Callable<Long> callback(PathSignatureContext context) throws Exception {
				return () -> {
					Path filePath = Paths.get(context.fileUploadRequestValue(FileUploadRequest::getUploadFile));
					if (Files.exists(filePath) && filePath.toFile().length() == file.getSize()) {
						return context.destory(() -> filePath.toFile().length());
					}
					
					long totalSize = new BigDecimal(context.fileUploadRequestValue(FileUploadRequest::getSize))
							.longValue();
					long chunkSize = new BigDecimal(context.fileUploadRequestValue(FileUploadRequest::getChunkSize))
							.longValue();
					long chunkCount = totalSize / chunkSize + (totalSize % chunkSize > 0 ? 1 : 0);
					if (Files.notExists(filePath)) {
						Files.createFile(filePath);
					}
					for (int index = 0; index < chunkCount; index++) {
						FileUploadChunkData fileUploadChunkData = new FileUploadChunkData(context, requestId, index);
						Path chunkPath = Paths.get(fileUploadChunkData.getUploadFile());
						Files.write(filePath, Files.readAllBytes(chunkPath), StandardOpenOption.APPEND);
						Files.deleteIfExists(chunkPath);
					}
					return context.destory(() -> filePath.toFile().length());
				};
			}

			@Override
			public void init(FileUploadRequest request) {
				request.init(new FileUploadRequestData() {

					@Override
					public String getUploadPath() {
						return uploadPath;
					}

					@Override
					public String getSize() {
						return String.valueOf(file.getSize());
					}

					@Override
					public String getOriginalFilename() {
						return file.getOriginalFilename();
					}

					@Override
					public String getName() {
						return file.getName();
					}

					@Override
					public String getHash() {
						return requestId;
					}

					@Override
					public String getChunkSize() {
						return getDefaultChunkSize();
					}

					@Override
					public String getContentType() {
						return file.getContentType();
					}
				});
			}

		};
		Callable<Long> callable = provider.buildTaskCallback(requestId, signature, new HashMap<>());
		return ResponseEntity.ok().body(callable.call());
	}

}
