package cn.jovanyframework.cloud.saasclient.resource;

import java.security.Principal;
import java.text.MessageFormat;

public final class ScenePrincipal implements Principal {
	private final String clientId;

	public ScenePrincipal(String clientId) {
		this.clientId = clientId;
	}

	public ScenePrincipal(String namespace, String target) {
		this.clientId = MessageFormat.format("{0}-{1}", namespace, target);
	}

	@Override
	public String getName() {
		return clientId;
	}

	public String getNamespace() {
		return clientId.contains("-") ? clientId.split("-")[0] : null;
	}

	public String getTarget() {
		return clientId.contains("-") ? clientId.split("-")[1] : clientId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
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
		ScenePrincipal other = (ScenePrincipal) obj;
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId))
			return false;
		return true;
	}

}