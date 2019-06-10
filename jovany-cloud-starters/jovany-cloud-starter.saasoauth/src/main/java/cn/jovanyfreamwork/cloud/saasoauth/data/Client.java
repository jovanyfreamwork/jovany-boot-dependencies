package cn.jovanyfreamwork.cloud.saasoauth.data;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Client {

	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.EAGER)
	private Tenant tenant;

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	private boolean secretRequired;

	public boolean isSecretRequired() {
		return secretRequired;
	}

	public void setSecretRequired(boolean secretRequired) {
		this.secretRequired = secretRequired;
	}

	private String clientSecret;

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	private boolean scoped;

	public boolean isScoped() {
		return scoped;
	}

	public void setScoped(boolean scoped) {
		this.scoped = scoped;
	}

	private int accessTokenValiditySeconds;

	public int getAccessTokenValiditySeconds() {
		return accessTokenValiditySeconds;
	}

	public void setAccessTokenValiditySeconds(int accessTokenValiditySeconds) {
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
	}

	private int refreshTokenValiditySeconds;

	public int getRefreshTokenValiditySeconds() {
		return refreshTokenValiditySeconds;
	}

	public void setRefreshTokenValiditySeconds(int refreshTokenValiditySeconds) {
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
	}

	@OneToMany(fetch = FetchType.EAGER)
	private Set<Scope> scopes;

	public Set<Scope> getScopes() {
		return scopes;
	}

	public void setScopes(Set<Scope> scopes) {
		this.scopes = scopes;
	}

	@OneToMany(fetch = FetchType.EAGER)
	private Set<AuthorizedGrantType> authorizedGrantTypes;

	public Set<AuthorizedGrantType> getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}

	public void setAuthorizedGrantTypes(Set<AuthorizedGrantType> authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}

	private String registeredRedirectUri;

	public String getRegisteredRedirectUri() {
		return registeredRedirectUri;
	}

	public void setRegisteredRedirectUri(String registeredRedirectUri) {
		this.registeredRedirectUri = registeredRedirectUri;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<ClientAuthority> authoritys;

	public Set<ClientAuthority> getAuthoritys() {
		return authoritys;
	}

	public void setAuthoritys(Set<ClientAuthority> authoritys) {
		this.authoritys = authoritys;
	}

}