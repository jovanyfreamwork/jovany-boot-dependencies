package cn.jovanyframework.cloud.fileupload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableFileUploadServer
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
