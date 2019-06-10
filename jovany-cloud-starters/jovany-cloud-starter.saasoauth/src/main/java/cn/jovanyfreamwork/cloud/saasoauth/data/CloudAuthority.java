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
public class CloudAuthority {

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
	private CloudAuthority parent;

	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
	private Set<CloudAuthority> children;

	public CloudAuthority getParent() {
		return parent;
	}

	public void setParent(CloudAuthority parent) {
		this.parent = parent;
	}

	public Set<CloudAuthority> getChildren() {
		return children;
	}

	public void setChildren(Set<CloudAuthority> children) {
		this.children = children;
	}

	
	@ManyToMany(mappedBy = "authoritys")
	private Set<Cloud> clouds;

	public Set<Cloud> getClouds() {
		return clouds;
	}

	public void setClouds(Set<Cloud> clouds) {
		this.clouds = clouds;
	}

}
