package cn.jovanyfreamwork.cloud.saas.data;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity(name = "saas_scene")
public class Scene {

	public Scene(SceneID id) {
		super();
		this.id = id;
	}

	public Scene(String id) {
		super();
		this.id = new SceneID(id);
	}

	public Scene() {
		super();
	}

	@EmbeddedId
	private SceneID id;

	@Column(length = 100)
	private String details;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "id.scene")
	private Set<Scope> scopes;

	public SceneID getId() {
		return id;
	}

	public void setId(SceneID id) {
		this.id = id;
	}

	public String getNamespace() {
		return id.getNamespace();
	}

	public String getTarget() {
		return id.getTarget();
	}

	public Set<Scope> getScopes() {
		return scopes;
	}

	public void setScopes(Set<Scope> scopes) {
		this.scopes = scopes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Scene other = (Scene) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
