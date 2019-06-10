package cn.jovanyfreamwork.cloud.saasoauth.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import cn.jovanyfreamwork.cloud.saasoauth.core.authority.GrantedAppAuthority;
import cn.jovanyfreamwork.cloud.saasoauth.data.App;
import cn.jovanyfreamwork.cloud.saasoauth.data.AppAuthority;

public class AppTenant extends CloudTenant {

	private final App app;

	public AppTenant(App app) {
		super(app.getCloud());
		this.app = app;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> result = new HashSet<>(super.getAuthorities());
		Set<AppAuthority> authoritys = app.getAuthoritys();
		initAuthorities(result, authoritys);
		return result;
	}

	private final void initAuthorities(Set<GrantedAuthority> result, Set<AppAuthority> authoritys) {
		if (authoritys == null || authoritys.isEmpty()) {
			return;
		}
		authoritys.forEach(authority -> {
			result.add(new GrantedAppAuthority(authority));
			initAuthorities(result, authority.getChildren());
		});
	}

	public App getApp() {
		return app;
	}

}
