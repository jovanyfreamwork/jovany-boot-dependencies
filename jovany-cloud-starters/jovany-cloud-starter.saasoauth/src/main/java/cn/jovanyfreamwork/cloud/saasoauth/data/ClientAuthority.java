package cn.jovanyfreamwork.cloud.saasoauth.data;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ClientAuthority {

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

	@ManyToOne
	private ClientAuthority parent;

	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
	private Set<ClientAuthority> children;

	public ClientAuthority getParent() {
		return parent;
	}

	public void setParent(ClientAuthority parent) {
		this.parent = parent;
	}

	public Set<ClientAuthority> getChildren() {
		return children;
	}

	public void setChildren(Set<ClientAuthority> children) {
		this.children = children;
	}

	@ManyToMany(mappedBy = "authoritys")
	private Set<Client> clients;

	public Set<Client> getClients() {
		return clients;
	}

	public void setClients(Set<Client> clients) {
		this.clients = clients;
	}

}
