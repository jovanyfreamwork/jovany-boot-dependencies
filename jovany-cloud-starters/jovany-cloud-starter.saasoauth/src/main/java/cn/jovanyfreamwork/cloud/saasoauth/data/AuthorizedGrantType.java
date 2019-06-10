package cn.jovanyfreamwork.cloud.saasoauth.data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AuthorizedGrantType {

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

}
