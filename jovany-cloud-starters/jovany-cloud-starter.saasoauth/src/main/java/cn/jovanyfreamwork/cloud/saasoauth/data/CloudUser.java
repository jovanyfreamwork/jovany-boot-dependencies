package cn.jovanyfreamwork.cloud.saasoauth.data;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class CloudUser {

	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	private Cloud cloud;

	public Cloud getCloud() {
		return cloud;
	}

	public void setCloud(Cloud cloud) {
		this.cloud = cloud;
	}

	@OneToMany(mappedBy = "cloudUser")
	private Set<AppUser> appUsers;

	public Set<AppUser> getAppUsers() {
		return appUsers;
	}

	public void setAppUsers(Set<AppUser> appUsers) {
		this.appUsers = appUsers;
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
	private Set<CloudUserAuthority> authoritys;

	public Set<CloudUserAuthority> getAuthoritys() {
		return authoritys;
	}

	public void setAuthoritys(Set<CloudUserAuthority> authoritys) {
		this.authoritys = authoritys;
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