package cn.jovanyframework.cloud.saasclient.scene;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class SaasClientSecurityConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private final SceneService sceneService;

	public SaasClientSecurityConfigurer(SceneService sceneService) {
		super();
		this.sceneService = sceneService;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		SaasBearerAuthenticationFilter saasAuthenticationFilter = new SaasBearerAuthenticationFilter();
		saasAuthenticationFilter.setSceneService(sceneService);
		http.addFilterAt(saasAuthenticationFilter, BasicAuthenticationFilter.class);
	}

}
