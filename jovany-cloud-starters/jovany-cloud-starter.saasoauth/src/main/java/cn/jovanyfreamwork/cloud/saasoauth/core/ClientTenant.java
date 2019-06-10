package cn.jovanyfreamwork.cloud.saasoauth.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import cn.jovanyfreamwork.cloud.saasoauth.core.authority.GrantedClientAuthority;
import cn.jovanyfreamwork.cloud.saasoauth.data.AuthorizedGrantType;
import cn.jovanyfreamwork.cloud.saasoauth.data.Client;
import cn.jovanyfreamwork.cloud.saasoauth.data.ClientAuthority;
import cn.jovanyfreamwork.cloud.saasoauth.data.Scope;

public class ClientTenant implements ClientDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Client client;

	private Map<String, Object> additionalInformation = new HashMap<>();

	public ClientTenant(Client client) {
		super();
		this.client = client;
	}

	public Object putAdditionalInformation(String key, Object value) {
		return additionalInformation.put(key, value);
	}

	@Override
	public String getClientId() {
		return client.getId();
	}

	@Override
	public Set<String> getResourceIds() {
		return null;
	}

	@Override
	public boolean isSecretRequired() {
		return client.isSecretRequired();
	}

	@Override
	public String getClientSecret() {
		return client.getClientSecret();
	}

	@Override
	public boolean isScoped() {
		return client.isScoped();
	}

	@Override
	public Set<String> getScope() {
		return client.getScopes().stream().map(Scope::getName).collect(Collectors.toSet());
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		return client.getAuthorizedGrantTypes().stream().map(AuthorizedGrantType::getName).collect(Collectors.toSet());
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		return Arrays.asList(client.getRegisteredRedirectUri().split(",")).stream().collect(Collectors.toSet());
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> result = new HashSet<>();
		Set<ClientAuthority> authoritys = client.getAuthoritys();
		initAuthorities(result, authoritys);
		return result;
	}

	private final void initAuthorities(Set<GrantedAuthority> result, Set<ClientAuthority> authoritys) {
		if (authoritys == null || authoritys.isEmpty()) {
			return;
		}
		authoritys.forEach(authority -> {
			result.add(new GrantedClientAuthority(authority));
			initAuthorities(result, authority.getChildren());
		});
	}

	@Override
	public Integer getAccessTokenValiditySeconds() {
		return client.getAccessTokenValiditySeconds();
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return client.getRefreshTokenValiditySeconds();
	}

	@Override
	public boolean isAutoApprove(String scope) {
		return client.getScopes().stream().map(Scope::getName).filter(scope::equals).count() > 0;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		return additionalInformation;
	}

}
