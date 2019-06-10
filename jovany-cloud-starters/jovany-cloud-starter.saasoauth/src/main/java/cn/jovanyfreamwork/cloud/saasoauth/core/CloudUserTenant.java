package cn.jovanyfreamwork.cloud.saasoauth.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import cn.jovanyfreamwork.cloud.saasoauth.core.authority.GrantedCloudUserAuthority;
import cn.jovanyfreamwork.cloud.saasoauth.data.CloudUser;
import cn.jovanyfreamwork.cloud.saasoauth.data.CloudUserAuthority;

public class CloudUserTenant extends UserTenant {

	private final CloudUser cloudUser;

	public CloudUserTenant(CloudUser cloudUser) {
		super(cloudUser.getUser());
		this.cloudUser = cloudUser;
	}

	public CloudUser getCloudUser() {
		return cloudUser;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getUnionId() {
		return cloudUser.getId();
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> result = new HashSet<>(super.getAuthorities());
		Set<CloudUserAuthority> authoritys = cloudUser.getAuthoritys();
		initAuthorities(result, authoritys);
		return result;
	}

	private final void initAuthorities(Set<GrantedAuthority> result, Set<CloudUserAuthority> authoritys) {
		if (authoritys == null || authoritys.isEmpty()) {
			return;
		}
		authoritys.forEach(authority -> {
			result.add(new GrantedCloudUserAuthority(authority));
			initAuthorities(result, authority.getChildren());
		});
	}

}
