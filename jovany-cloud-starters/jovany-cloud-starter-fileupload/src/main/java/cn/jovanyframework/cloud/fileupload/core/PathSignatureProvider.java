package cn.jovanyframework.cloud.fileupload.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

import cn.jovany.ffmpeg.signature.SignatureProvider;
import cn.jovany.ffmpeg.signature.SignatureToken;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

public class PathSignatureProvider implements SignatureProvider {

	private final Path path;
	private final Path signaturePath;
	private final Path lockPath;

	public PathSignatureProvider(Path path) {
		this.path = path;
		this.signaturePath = Paths.get(path.toString() + ".signature");
		this.lockPath = Paths.get(path.toString() + ".lock");
	}

	public PathSignatureProvider(String path) {
		this(Paths.get(path));
	}

	@Override
	public boolean existsSignature() {
		return Files.exists(signaturePath);
	}

	@Override
	public byte[] readSignature() throws IOException {
		return Files.readAllBytes(signaturePath);
	}

	@Override
	public void writeSignature(byte[] signature) throws IOException {
		Files.write(signaturePath, signature);
	}

	@Override
	public void deleteSignature() throws IOException {
		Files.deleteIfExists(signaturePath);
	}

	@Override
	public boolean isLocked() throws IOException {
		return Files.exists(lockPath);
	}

	@Override
	public void lockSignature() throws IOException {
		if (Files.notExists(lockPath))
			Files.createFile(lockPath);
	}

	@Override
	public void unlockSignature() throws IOException {
		Files.deleteIfExists(lockPath);
	}

	public PathSignatureContext context(FileUploadRequest token) {
		return new PathSignatureContext(token);
	}

	public class PathSignatureContext {

		private final FileUploadRequest request;
		private final SignatureToken token;

		public PathSignatureContext(FileUploadRequest token) {
			super();
			this.request = token;
			this.token = token.getToken();
		}

		public Path getPath() {
			return path;
		}

		public Path getSignaturePath() {
			return signaturePath;
		}

		public Path getLockPath() {
			return lockPath;
		}

		public <R> R fileUploadRequestValue(Function<FileUploadRequest, R> func) {
			return func.apply(request);
		}

		public <R> R tokenValue(Function<SignatureToken, R> func) {
			return func.apply(token);
		}

		public <R> R providerValue(Function<PathSignatureProvider, R> func) {
			return func.apply(PathSignatureProvider.this);
		}
		
		public void removelock() throws IOException {
			unlockSignature();
		}
		
		public void providerAccept(Consumer<PathSignatureProvider> func) {
			 func.accept(PathSignatureProvider.this);
		}

		public void destroy() throws IOException {
			unlock(token);
		}
		
		public <R> R destory(Callable<R> call) throws Exception {
			unlock(token);
			return call.call();
		}
		
		public Path directory(String... more) throws IOException, TemplateException {
			return directory(new HashMap<>(), more);
		}

		/**
		 * 获取子路径并将子路径替换模版参数
		 * 
		 * @param params
		 * @param more
		 * @return
		 * @throws IOException
		 * @throws TemplateException
		 */
		public Path directory(Map<String, Object> params, String... more) throws IOException, TemplateException {
			if (!Files.isDirectory(PathSignatureProvider.this.path)) {
				throw new FileNotFoundException(PathSignatureProvider.this.path.toString());
			}
			params.putAll(token.getData());
			List<String> morelist = new ArrayList<String>();
			for (String tmp : more) {
				morelist.add(template(tmp, params));
			}
			Path path = Paths.get(PathSignatureProvider.this.path.toString(), morelist.toArray(new String[] {}));

			if (Files.isDirectory(path)) {
				Files.createDirectories(path);
			} else {
				Files.createDirectories(path.getParent());
			}
			return path;
		}

		public String template(String tpl) throws IOException, TemplateException {
			return template(tpl, new HashMap<>());
		}

		/**
		 * 模版字符串支持token参数和环境变量
		 * 
		 * @param tpl
		 * @param params
		 * @return
		 * @throws IOException
		 * @throws TemplateException
		 */
		public String template(String tpl, Map<String, Object> params) throws IOException, TemplateException {
			params.putAll(token.getData());
			params.putAll(token.getEnv());
			Template template = new Template("strTpl", tpl, new Configuration(new Version("2.3.23")));
			StringWriter result = new StringWriter();
			template.process(params, result);
			return result.toString();
		}

	}

}
