package cn.jovanyfreamwork.cloud.saasoauth.core.authority;

import org.springframework.security.core.GrantedAuthority;

import cn.jovanyfreamwork.cloud.saasoauth.data.UserAuthority;

public class GrantedUserAuthority implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final UserAuthority userAuthority;

	public GrantedUserAuthority(UserAuthority userAuthority) {
		super();
		this.userAuthority = userAuthority;
	}

	@Override
	public String getAuthority() {
		return userAuthority.getName();
	}

	public UserAuthority getUserAuthority() {
		return userAuthority;
	}

}
