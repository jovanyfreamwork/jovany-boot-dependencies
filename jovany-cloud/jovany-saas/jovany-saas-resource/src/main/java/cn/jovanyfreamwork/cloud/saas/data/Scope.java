package cn.jovanyfreamwork.cloud.saas.data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity(name = "saas_scope")
public class Scope {

	public Scope() {
		super();
	}

	@EmbeddedId
	private ScopeID id;

	public ScopeID getId() {
		return id;
	}

	public void setId(ScopeID id) {
		this.id = id;
	}

	public String getScope() {
		return id.getId();
	}

	public String getSceneNamespace() {
		return id.getScene().getId().getNamespace();
	}

	public String getSceneTarget() {
		return id.getScene().getId().getTarget();
	}

	public String getScene() {
		return id.getScene().getId().toString();
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
		Scope other = (Scope) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
