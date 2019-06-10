package cn.jovanyfreamwork.cloud.saasoauth.core.authority;

import cn.jovanyfreamwork.cloud.saasoauth.data.CloudUserAuthority;

public class GrantedCloudUserAuthority extends GrantedUserAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GrantedCloudUserAuthority(CloudUserAuthority cloudUserAuthority) {
		super(cloudUserAuthority.getUserAuthority());
	}

}
