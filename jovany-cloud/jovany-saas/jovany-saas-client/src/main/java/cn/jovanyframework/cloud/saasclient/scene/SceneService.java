package cn.jovanyframework.cloud.saasclient.scene;

import org.springframework.security.core.AuthenticationException;

public interface SceneService {

	SaaSAuthenticationToken loadSceneByToken(String token) throws AuthenticationException;
	
}
