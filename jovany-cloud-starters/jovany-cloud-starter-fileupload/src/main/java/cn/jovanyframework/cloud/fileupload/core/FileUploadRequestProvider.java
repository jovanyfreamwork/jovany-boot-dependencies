package cn.jovanyframework.cloud.fileupload.core;

import java.util.concurrent.Callable;

import cn.jovany.ffmpeg.signature.SignatureProvider;
import cn.jovany.ffmpeg.signature.SignatureTaskExecuter;
import cn.jovany.ffmpeg.signature.SignatureToken;
import cn.jovanyframework.cloud.fileupload.core.PathSignatureProvider.PathSignatureContext;

public interface FileUploadRequestProvider<R> extends SignatureTaskExecuter<R> {

	@Override
	public default Callable<R> callback(SignatureToken token, SignatureProvider provider) throws Exception {
		FileUploadRequest request = new FileUploadRequest(token);
		PathSignatureContext context = ((PathSignatureProvider) provider).context(request);
		return callback(context);
	}

	Callable<R> callback(PathSignatureContext context) throws Exception;

	void init(FileUploadRequest request);

	@Override
	public default SignatureProvider signatureProvider(SignatureToken token) throws Exception {
		FileUploadRequest request = new FileUploadRequest(token);
		init(request);
		return new PathSignatureProvider(request.getUploadFile());
	}

}
