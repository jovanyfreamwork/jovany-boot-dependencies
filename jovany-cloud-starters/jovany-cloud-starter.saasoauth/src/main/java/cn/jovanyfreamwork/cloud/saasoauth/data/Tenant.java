package cn.jovanyfreamwork.cloud.saasoauth.data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Tenant {

	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	private Schema schema;

	public Schema getSchema() {
		return schema;
	}

	public void setSchema(Schema schema) {
		this.schema = schema;
	}

	@OneToOne(mappedBy = "tenant")
	private CloudUser cloudUser;

	public CloudUser getCloudUser() {
		return cloudUser;
	}

	public void setCloudUser(CloudUser cloudUser) {
		this.cloudUser = cloudUser;
	}

	@OneToOne(mappedBy = "tenant")
	private AppUser appUser;

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	@OneToOne(mappedBy = "tenant")
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
