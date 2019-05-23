package cn.jovanyfreamwork.cloud.saas.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SceneID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String SPLIT = "-";

	@Column(length = 40)
	private String namespace;
	@Column(length = 40)
	private String target;

	public SceneID() {
		super();
	}

	public SceneID(String key) {
		super();
		if (key.contains(SPLIT)) {
			String[] keyArr = key.split(SPLIT);
			this.namespace = keyArr[0];
			this.target = keyArr[1];
		} else {
			this.namespace = "";
			this.target = key;
		}
	}

	public SceneID(String namespace, String target) {
		super();
		this.namespace = namespace;
		this.target = target;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((namespace == null) ? 0 : namespace.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
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
		SceneID other = (SceneID) obj;
		if (namespace == null) {
			if (other.namespace != null)
				return false;
		} else if (!namespace.equals(other.namespace))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(namespace);
		builder.append(namespace.length() == 0 ? "" : SPLIT);
		builder.append(target);
		return builder.toString();
	}

}
