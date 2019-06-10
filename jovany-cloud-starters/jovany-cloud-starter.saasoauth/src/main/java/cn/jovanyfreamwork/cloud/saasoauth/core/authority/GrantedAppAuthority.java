package cn.jovanyfreamwork.cloud.saasoauth.core.authority;

import cn.jovanyfreamwork.cloud.saasoauth.data.AppAuthority;

public class GrantedAppAuthority extends GrantedClientAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GrantedAppAuthority(AppAuthority appAuthority) {
		super(appAuthority.getClientAuthority());
	}

}
