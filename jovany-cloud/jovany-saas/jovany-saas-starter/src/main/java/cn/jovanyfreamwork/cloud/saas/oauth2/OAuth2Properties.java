package cn.jovanyfreamwork.cloud.saas.oauth2;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "config")
public class OAuth2Properties {

	private Root root;

	public static class Root {

		private Client client;
		private User user;

		public static class Client {
			private String clientId;
			private String clientSecret;
			private String[] authorizedGrantTypes;
			private String[] scope;
			private String[] resourceIds;

			public String getClientId() {
				return clientId;
			}

			public void setClientId(String clientId) {
				this.clientId = clientId;
			}

			public String getClientSecret() {
				return clientSecret;
			}

			public void setClientSecret(String clientSecret) {
				this.clientSecret = clientSecret;
			}

			public String[] getAuthorizedGrantTypes() {
				return authorizedGrantTypes;
			}

			public void setAuthorizedGrantTypes(String[] authorizedGrantTypes) {
				this.authorizedGrantTypes = authorizedGrantTypes;
			}

			public String[] getScope() {
				return scope;
			}

			public void setScope(String[] scope) {
				this.scope = scope;
			}

			public String[] getResourceIds() {
				return resourceIds;
			}

			public void setResourceIds(String[] resourceIds) {
				this.resourceIds = resourceIds;
			}

		}

		public static class User {
			private String username;
			private String password;
			private String[] authorities;

			public String getUsername() {
				return username;
			}

			public void setUsername(String username) {
				this.username = username;
			}

			public String getPassword() {
				return password;
			}

			public void setPassword(String password) {
				this.password = password;
			}

			public String[] getAuthorities() {
				return authorities;
			}

			public void setAuthorities(String[] authorities) {
				this.authorities = authorities;
			}

		}

		public Client getClient() {
			return client;
		}

		public void setClient(Client client) {
			this.client = client;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

	}

	public Root getRoot() {
		return root;
	}

	public void setRoot(Root root) {
		this.root = root;
	}
}
