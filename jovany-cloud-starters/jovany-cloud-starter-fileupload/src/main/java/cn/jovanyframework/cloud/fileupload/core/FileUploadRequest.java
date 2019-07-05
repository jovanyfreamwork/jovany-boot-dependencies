package cn.jovanyframework.cloud.fileupload.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.web.multipart.MultipartFile;

import cn.jovany.ffmpeg.signature.SignatureToken;

public class FileUploadRequest implements FileUploadRequestData {

	private final SignatureToken token;

	public FileUploadRequest(SignatureToken token) {
		super();
		this.token = token;
	}

	public String getName() {
		return token.getData().get("name").toString();
	}

	public String getOriginalFilename() {
		return token.getData().get("filename").toString();
	}

	public String getSize() {
		return token.getData().get("size").toString();
	}

	public void init(MultipartFile file, String uploadPath) {
		token.getData().putAll(TokenChains.bundle(file, MultipartFileToData));
		token.getEnv().putAll(TokenChains.one(KEY_UPLOAD_PATH, uploadPath));
	}

	public void init(FileUploadRequestData fileUploadRequestData) {
		setContextType(fileUploadRequestData.getContentType());
		setName(fileUploadRequestData.getName());
		setOriginalFilename(fileUploadRequestData.getOriginalFilename());
		setSize(fileUploadRequestData.getSize());
		setUploadPath(fileUploadRequestData.getUploadPath());
		setHash(fileUploadRequestData.getHash());
		setChuckSize(fileUploadRequestData.getChunkSize());
	}

	public void setName(String name) {
		token.getData().put("name", name);
	}

	public void setOriginalFilename(String filename) {
		token.getData().put("filename", filename);
	}

	public void setHash(String hash) {
		token.getData().put("hash", hash);
	}

	public void setSize(String size) {
		token.getData().put("size", size);
	}

	public String getHash() {
		return token.getData().get("hash").toString();
	}

	public String getUploadPath() {
		return token.getEnv().get(KEY_UPLOAD_PATH).toString();
	}

	public void setUploadPath(String path) {
		token.getEnv().put(KEY_UPLOAD_PATH, path);
	}

	private static final Function<MultipartFile, Map<String, Object>> MultipartFileToData = new Function<MultipartFile, Map<String, Object>>() {
		@Override
		public Map<String, Object> apply(MultipartFile t) {
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("name", t.getName());
			data.put("filename", t.getOriginalFilename());
			data.put("type", t.getContentType());
			data.put("size", String.valueOf(t.getSize()));
			data.put("hash", String.valueOf(t.hashCode()));
			return data;
		}
	};

	private static final String KEY_UPLOAD_PATH = "uploadPath";

	public static final class TokenChains {

		@SafeVarargs
		public static Map<String, Object> merge(Map<String, Object>... maps) {
			return new TokenChains(maps).map;
		}

		public static Map<String, Object> one(String key, Object value) {
			return new TokenChains(key, value).map;
		}

		public static Map<String, Object> bundle(MultipartFile file,
				Function<MultipartFile, Map<String, Object>> func) {
			return new TokenChains(file, func).map;
		}

		private final Map<String, Object> map = new HashMap<String, Object>();

		@SafeVarargs
		private TokenChains(Map<String, Object>... maps) {
			Arrays.asList(maps).forEach(this.map::putAll);
		}

		private TokenChains(String key, Object value) {
			map.put(key, value);
		}

		private TokenChains(MultipartFile file, Function<MultipartFile, Map<String, Object>> func) {
			map.putAll(func.apply(file));
		}

	}

	public SignatureToken getToken() {
		return token;
	}

	public <R> R tokenValue(Function<SignatureToken, R> func) {
		return func.apply(token);
	}

	@Override
	public String getChunkSize() {
		return token.getEnv().get("chunk-size").toString();
	}

	public void setChuckSize(String chuckSize) {
		token.getEnv().put("chunk-size", chuckSize);
	}

	@Override
	public String getContentType() {
		return token.getData().get("type").toString();
	}

	public void setContextType(String type) {
		token.getData().put("type", type);
	}

}
