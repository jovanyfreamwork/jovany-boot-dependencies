package cn.jovanyfreamwork.cloud.saasoauth.data;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class UserAuthority {

	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "userAuthority")
	private AppUserAuthority appUserAuthority;

	public AppUserAuthority getAppUserAuthority() {
		return appUserAuthority;
	}

	public void setAppUserAuthority(AppUserAuthority appUserAuthority) {
		this.appUserAuthority = appUserAuthority;
	}

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "userAuthority")
	private CloudUserAuthority cloudUserAuthority;

	public CloudUserAuthority getCloudUserAuthority() {
		return cloudUserAuthority;
	}

	public void setCloudUserAuthority(CloudUserAuthority cloudUserAuthority) {
		this.cloudUserAuthority = cloudUserAuthority;
	}

	@ManyToOne
	private UserAuthority parent;

	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
	private Set<UserAuthority> children;

	public UserAuthority getParent() {
		return parent;
	}

	public void setParent(UserAuthority parent) {
		this.parent = parent;
	}

	public Set<UserAuthority> getChildren() {
		return children;
	}

	public void setChildren(Set<UserAuthority> children) {
		this.children = children;
	}

	@ManyToMany(mappedBy = "userAuthoritys")
	private Set<User> users;

	public Set<User> getUsers() {
		return users;
	}

	public void setAppUsers(Set<User> users) {
		this.users = users;
	}
	
	

}
