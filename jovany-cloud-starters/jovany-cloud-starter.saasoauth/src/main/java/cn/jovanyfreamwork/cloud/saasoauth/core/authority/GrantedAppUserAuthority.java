package cn.jovanyfreamwork.cloud.saasoauth.core.authority;

import cn.jovanyfreamwork.cloud.saasoauth.data.AppUserAuthority;

public class GrantedAppUserAuthority extends GrantedUserAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GrantedAppUserAuthority(AppUserAuthority appUserAuthority) {
		super(appUserAuthority.getUserAuthority());
	}

}
