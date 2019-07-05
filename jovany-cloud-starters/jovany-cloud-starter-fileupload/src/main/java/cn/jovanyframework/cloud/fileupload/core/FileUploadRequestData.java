package cn.jovanyframework.cloud.fileupload.core;

import java.nio.file.Path;
import java.nio.file.Paths;


public interface FileUploadRequestData {

	String getName();

	String getOriginalFilename();

	String getSize();
	
	String getContentType();

	String getHash();

	String getUploadPath();
	
	String getChunkSize();

	public default String getUploadFile() {
		String filename = getOriginalFilename();
		String suffix = filename.substring(filename.lastIndexOf('.'));
		Path path = Paths.get(getUploadPath(), getHash() + suffix);
		return path.toString();
	}
}
