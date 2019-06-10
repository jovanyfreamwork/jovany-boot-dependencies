package cn.jovanyfreamwork.cloud.saasoauth.core;

import cn.jovanyfreamwork.cloud.saasoauth.data.AppUser;

public class AppUserTenant extends CloudUserTenant {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final AppUser appUser;
	
	public AppUserTenant(AppUser appUser) {
		super(appUser.getCloudUser());
		this.appUser = appUser;	
	}
	
	public String getOpenId() {
		return appUser.getId();
	}

	public AppUser getAppUser() {
		return appUser;
	}

}
