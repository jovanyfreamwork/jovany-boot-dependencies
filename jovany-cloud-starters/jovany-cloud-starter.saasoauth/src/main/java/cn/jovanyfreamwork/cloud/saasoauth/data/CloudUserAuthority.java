package cn.jovanyfreamwork.cloud.saasoauth.data;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class CloudUserAuthority {

	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	private UserAuthority userAuthority;

	public UserAuthority getUserAuthority() {
		return userAuthority;
	}

	public void setUserAuthority(UserAuthority userAuthority) {
		this.userAuthority = userAuthority;
	}
	
	@ManyToOne
	private CloudUserAuthority parent;

	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
	private Set<CloudUserAuthority> children;

	public CloudUserAuthority getParent() {
		return parent;
	}

	public void setParent(CloudUserAuthority parent) {
		this.parent = parent;
	}

	public Set<CloudUserAuthority> getChildren() {
		return children;
	}

	public void setChildren(Set<CloudUserAuthority> children) {
		this.children = children;
	}

	
	
	@ManyToMany(mappedBy = "userAuthoritys")
	private Set<CloudUser> users;

	public Set<CloudUser> getUsers() {
		return users;
	}

	public void setAppUsers(Set<CloudUser> users) {
		this.users = users;
	}
}
