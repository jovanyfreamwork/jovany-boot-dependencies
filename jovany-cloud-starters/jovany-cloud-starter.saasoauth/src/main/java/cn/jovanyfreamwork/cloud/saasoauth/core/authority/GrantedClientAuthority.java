package cn.jovanyfreamwork.cloud.saasoauth.core.authority;

import org.springframework.security.core.GrantedAuthority;

import cn.jovanyfreamwork.cloud.saasoauth.data.ClientAuthority;

public class GrantedClientAuthority implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final ClientAuthority clientAuthority;

	public GrantedClientAuthority(ClientAuthority clientAuthority) {
		super();
		this.clientAuthority = clientAuthority;
	}

	@Override
	public String getAuthority() {
		return clientAuthority.getName();
	}

	public ClientAuthority getClientAuthority() {
		return clientAuthority;
	}

}
