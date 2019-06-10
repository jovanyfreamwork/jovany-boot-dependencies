package cn.jovanyfreamwork.cloud.saasoauth.data;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class DataSource {

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

	@OneToMany(mappedBy = "dataSource")
	private Set<Schema> schemas;

	public Set<Schema> getSchemas() {
		return schemas;
	}

	public void setSchemas(Set<Schema> schemas) {
		this.schemas = schemas;
	}

}
