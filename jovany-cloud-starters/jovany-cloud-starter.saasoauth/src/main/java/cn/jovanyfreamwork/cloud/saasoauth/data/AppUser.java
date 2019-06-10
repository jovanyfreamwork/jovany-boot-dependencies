package cn.jovanyfreamwork.cloud.saasoauth.data;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class AppUser {

	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	private App app;

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	private CloudUser cloudUser;

	public CloudUser getCloudUser() {
		return cloudUser;
	}

	public void setCloudUser(CloudUser cloudUser) {
		this.cloudUser = cloudUser;
	}

	@OneToOne(fetch = FetchType.EAGER)
	private Tenant tenant;

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<AppUserAuthority> userAuthoritys;

	public Set<AppUserAuthority> getUserAuthoritys() {
		return userAuthoritys;
	}

	public void setUserAuthoritys(Set<AppUserAuthority> userAuthoritys) {
		this.userAuthoritys = userAuthoritys;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	private Schema schema;
	@ManyToOne(fetch = FetchType.EAGER)
	private DataSource dataSource;

	public Schema getSchema() {
		return schema;
	}

	public void setSchema(Schema schema) {
		this.schema = schema;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
}