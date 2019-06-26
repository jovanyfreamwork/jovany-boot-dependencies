package cn.jovanyframework.cloud.filestore.core;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.jovany.command.CommandApi;
import cn.jovany.ffmpeg.OpensslApi;

@Configuration
public class FileStoreConfig {

	@Value("${command.path.ffmpeg}")
	private String ffmpegPath;

	@Value("${command.path.openssl}")
	private String opensslPath;

	@Bean
	public CommandApi ffmpeg() {
		return new CommandApi(Paths.get(ffmpegPath).toFile());
	}

	@Bean
	public OpensslApi opensslApi() {
		return new OpensslApi(Paths.get(opensslPath).toFile());
	}

}
