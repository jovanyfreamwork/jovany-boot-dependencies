package cn.jovanyframework.cloud.saasclient.scene;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import cn.jovanyframework.cloud.saasclient.resource.ScenePrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JwtSceneService implements SceneService {

	private final String signingKey;

	public JwtSceneService(String signingKey) {
		super();
		this.signingKey = signingKey;
	}

	@Override
	public SaaSAuthenticationToken loadSceneByToken(String token) throws AuthenticationException {
		try {
			Claims claims = Jwts.parser().setSigningKey(signingKey.getBytes("UTF-8")).parseClaimsJws(token).getBody();
			String clientId = claims.get("client_id", String.class);
			List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(
					Strings.join(claims.get("scope", ArrayList.class).iterator(), ','));
			SaaSAuthenticationToken authenticationToken = new SaaSAuthenticationToken(new ScenePrincipal(clientId),
					authorities);
			return authenticationToken;
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
				| IllegalArgumentException | UnsupportedEncodingException ex) {
			throw new BadCredentialsException(ex.getMessage(), ex);
		}
	}

}
