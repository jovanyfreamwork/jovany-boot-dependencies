package cn.jovanyframework.cloud.saasclient.scene;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaasClientConfig {

	@Bean
	public SaasClientSecurityConfigurer saasClientSecurityConfigurer(SceneService sceneService) {
		return new SaasClientSecurityConfigurer(sceneService);
	}

}
