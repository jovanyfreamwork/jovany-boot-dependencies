package cn.jovanyfreamwork.cloud.saasoauth.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import cn.jovanyfreamwork.cloud.saasoauth.core.authority.GrantedCloudAuthority;
import cn.jovanyfreamwork.cloud.saasoauth.data.Cloud;
import cn.jovanyfreamwork.cloud.saasoauth.data.CloudAuthority;

public class CloudTenant extends ClientTenant {

	private final Cloud cloud;

	public CloudTenant(Cloud cloud) {
		super(cloud.getClient());
		this.cloud = cloud;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> result = new HashSet<>(super.getAuthorities());
		Set<CloudAuthority> authoritys = cloud.getAuthoritys();
		initAuthorities(result, authoritys);
		return result;
	}

	private final void initAuthorities(Set<GrantedAuthority> result, Set<CloudAuthority> authoritys) {
		if (authoritys == null || authoritys.isEmpty()) {
			return;
		}
		authoritys.forEach(authority -> {
			result.add(new GrantedCloudAuthority(authority));
			initAuthorities(result, authority.getChildren());
		});
	}

	public Cloud getCloud() {
		return cloud;
	}

}
