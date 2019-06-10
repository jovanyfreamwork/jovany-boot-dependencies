package cn.jovanyfreamwork.cloud.saasoauth.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import cn.jovanyfreamwork.cloud.saasoauth.core.authority.GrantedUserAuthority;
import cn.jovanyfreamwork.cloud.saasoauth.data.User;
import cn.jovanyfreamwork.cloud.saasoauth.data.UserAuthority;

public class UserTenant implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final User user;

	public UserTenant(User user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> result = new HashSet<>();
		Set<UserAuthority> authoritys = user.getAuthoritys();
		initAuthorities(result, authoritys);
		return result;
	}

	private final void initAuthorities(Set<GrantedAuthority> result, Set<UserAuthority> authoritys) {
		if (authoritys == null || authoritys.isEmpty()) {
			return;
		}
		authoritys.forEach(authority -> {
			result.add(new GrantedUserAuthority(authority));
			initAuthorities(result, authority.getChildren());
		});
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return user.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return user.isAccountNonExpired();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return user.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return user.isEnabled();
	}

}
