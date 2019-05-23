package cn.jovanyfreamwork.cloud.saas.oauth2;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.jovanyfreamwork.cloud.saas.data.Scene;
import cn.jovanyfreamwork.cloud.saas.data.SceneID;
import cn.jovanyfreamwork.cloud.saas.data.Scope;
import cn.jovanyfreamwork.cloud.saas.data.ScopeID;
import cn.jovanyfreamwork.cloud.saas.jpa.SceneRepository;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Bean
	public TokenEnhancer tokenEnhancer() {
		ObjectMapper mapper = new ObjectMapper();
		return (OAuth2AccessToken accessToken, OAuth2Authentication authentication) -> {
			try {
				System.out.println(mapper.writeValueAsString(accessToken));
				System.out.println(mapper.writeValueAsString(authentication));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return accessToken;
		};
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("as466gf");
		return converter;
	}

	@Bean
	public JwtTokenStore jwtTokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
	@Autowired
	private OAuth2Properties config;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("isAuthenticated()").tokenKeyAccess("permitAll()");
	}

	@Autowired
	private SceneRepository sceneRepository;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientId -> {
			BaseClientDetails clientDetails = new BaseClientDetails();
			clientDetails.setClientId(config.getRoot().getClient().getClientId().trim());
			clientDetails.setClientSecret(config.getRoot().getClient().getClientSecret().trim());
			clientDetails.setAuthorizedGrantTypes(Arrays.asList("client_credentials"));
			clientDetails.setScope(Arrays.asList("all"));
			if (Objects.equals(clientId, clientDetails.getClientId())) {
				return clientDetails;
			}
			
			Scene scene = sceneRepository.findById(new SceneID(clientId)).get();
			if (scene != null) {
				clientDetails.setClientId(scene.getId().toString());
				clientDetails.setScope(scene.getScopes().stream().map(Scope::getId).map(ScopeID::getId).collect(Collectors.toSet()));
				return clientDetails;
			}
			
			throw new ClientRegistrationException(clientId);
		});
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
		endpoints.tokenStore(jwtTokenStore());
		endpoints.tokenEnhancer(enhancerChain);
		endpoints.accessTokenConverter(accessTokenConverter());
	}

}
