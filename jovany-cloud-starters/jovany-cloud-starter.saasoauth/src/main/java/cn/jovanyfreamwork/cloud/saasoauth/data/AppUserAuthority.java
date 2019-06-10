package cn.jovanyfreamwork.cloud.saasoauth.data;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class AppUserAuthority {

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

	@ManyToMany(mappedBy = "userAuthoritys")
	private Set<AppUser> users;

	public Set<AppUser> getUsers() {
		return users;
	}

	public void setAppUsers(Set<AppUser> users) {
		this.users = users;
	}

}
