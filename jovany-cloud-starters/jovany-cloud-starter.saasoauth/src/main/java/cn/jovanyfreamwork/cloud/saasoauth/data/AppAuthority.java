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
public class AppAuthority {

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
	private ClientAuthority clientAuthority;

	public ClientAuthority getClientAuthority() {
		return clientAuthority;
	}

	public void setClientAuthority(ClientAuthority clientAuthority) {
		this.clientAuthority = clientAuthority;
	}

	@ManyToOne
	private AppAuthority parent;

	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
	private Set<AppAuthority> children;

	public AppAuthority getParent() {
		return parent;
	}

	public void setParent(AppAuthority parent) {
		this.parent = parent;
	}

	public Set<AppAuthority> getChildren() {
		return children;
	}

	public void setChildren(Set<AppAuthority> children) {
		this.children = children;
	}

	
	@ManyToMany(mappedBy = "authoritys")
	private Set<App> apps;

	public Set<App> getApps() {
		return apps;
	}

	public void setClients(Set<App> apps) {
		this.apps = apps;
	}

}
