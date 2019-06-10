package cn.jovanyfreamwork.cloud.saasoauth.core.authority;

import cn.jovanyfreamwork.cloud.saasoauth.data.CloudAuthority;

public class GrantedCloudAuthority extends GrantedClientAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GrantedCloudAuthority(CloudAuthority cloudAuthority) {
		super(cloudAuthority.getClientAuthority());
	}

}
