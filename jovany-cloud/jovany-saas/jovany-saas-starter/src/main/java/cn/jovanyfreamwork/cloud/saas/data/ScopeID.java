package cn.jovanyfreamwork.cloud.saas.data;

import java.io.Serializable;
import java.text.MessageFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class ScopeID implements Serializable {

	private static final long serialVersionUID = 1L;

	public ScopeID() {
		super();
	}

	public ScopeID(Scene scene, String id) {
		super();
		this.scene = scene;
		this.id = id;
	}

	public ScopeID(String key) {
		super();
		this.scene = new Scene(new SceneID(key.split("@")[0]));
		this.id = key.split("@")[1];
	}

	@Override
	public String toString() {
		return MessageFormat.format("{0}@{1}", this.scene.getId(), this.id);
	}

	@ManyToOne
	private Scene scene;

	@Column(length = 80)
	private String id;

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((scene == null) ? 0 : scene.hashCode());
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
		ScopeID other = (ScopeID) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (scene == null) {
			if (other.scene != null)
				return false;
		} else if (!scene.equals(other.scene))
			return false;
		return true;
	}

}
