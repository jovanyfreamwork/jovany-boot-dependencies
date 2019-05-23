package cn.jovanyframework.cloud.saasclient.scene;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

public class SaasBearerAuthenticationFilter extends OncePerRequestFilter {

	// ~ Instance fields
	// ================================================================================================

	private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
	private AuthenticationEntryPoint authenticationEntryPoint;
	private boolean ignoreFailure = false;
	private String tokenParameter = "access_token";
	private SceneService sceneService;

	/**
	 * Creates an instance which will authenticate against the supplied
	 * {@code AuthenticationManager} and which will ignore failed authentication
	 * attempts, allowing the request to proceed down the filter chain.
	 *
	 * @param authenticationManager the bean to submit authentication requests to
	 */
	public SaasBearerAuthenticationFilter() {
		this.ignoreFailure = true;
	}

	/**
	 * Creates an instance which will authenticate against the supplied
	 * {@code AuthenticationManager} and use the supplied
	 * {@code AuthenticationEntryPoint} to handle authentication failures.
	 *
	 * @param authenticationManager    the bean to submit authentication requests to
	 * @param authenticationEntryPoint will be invoked when authentication fails.
	 *                                 Typically an instance of
	 *                                 {@link BasicAuthenticationEntryPoint}.
	 */
	public SaasBearerAuthenticationFilter(AuthenticationEntryPoint authenticationEntryPoint) {
		Assert.notNull(authenticationEntryPoint, "authenticationEntryPoint cannot be null");
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	// ~ Methods
	// ========================================================================================================

	@Override
	public void afterPropertiesSet() {
		if (!isIgnoreFailure()) {
			Assert.notNull(this.authenticationEntryPoint, "An AuthenticationEntryPoint is required");
		}
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final boolean debug = this.logger.isDebugEnabled();

		try {

			String token = extractAndDecodeToken(request);

			if (token == null || token.length() == 0) {
				throw new BadCredentialsException("");
			}

			SaaSAuthenticationToken authRequest = sceneService.loadSceneByToken(token);

			if (debug) {
				this.logger.debug(
						"Basic Authentication Authorization header found for user '" + authRequest.getName() + "'");
			}

			if (authenticationIsRequired(authRequest.getName())) {
				authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
				Authentication authResult = authRequest;

				if (debug) {
					this.logger.debug("Authentication success: " + authResult);
				}

				SecurityContextHolder.getContext().setAuthentication(authResult);

				onSuccessfulAuthentication(request, response, authResult);
			}

		} catch (AuthenticationException failed) {
			SecurityContextHolder.clearContext();

			if (debug) {
				this.logger.debug("Authentication request for failed: " + failed);
			}

			onUnsuccessfulAuthentication(request, response, failed);

			if (this.ignoreFailure) {
				chain.doFilter(request, response);
			} else {
				this.authenticationEntryPoint.commence(request, response, failed);
			}

			return;
		}

		chain.doFilter(request, response);
	}

	private String extractAndDecodeToken(HttpServletRequest request) {
		String authorizationHeaderValue = request.getHeader("Authorization");
		String tokenParameterValue = request.getParameter(this.tokenParameter);

		if (authorizationHeaderValue != null && authorizationHeaderValue.toLowerCase().startsWith("bearer ")) {
			return authorizationHeaderValue.substring(7);
		}

		return tokenParameterValue;
	}

	private boolean authenticationIsRequired(String username) {
		// Only reauthenticate if username doesn't match SecurityContextHolder and user
		// isn't authenticated
		// (see SEC-53)
		Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();

		if (existingAuth == null || !existingAuth.isAuthenticated()) {
			return true;
		}

		// Limit username comparison to providers which use usernames (ie
		// UsernamePasswordAuthenticationToken)
		// (see SEC-348)

		if (existingAuth instanceof UsernamePasswordAuthenticationToken && !existingAuth.getName().equals(username)) {
			return true;
		}

		if (existingAuth instanceof SaaSAuthenticationToken && !existingAuth.getName().equals(username)) {
			return true;
		}

		// Handle unusual condition where an AnonymousAuthenticationToken is already
		// present
		// This shouldn't happen very often, as BasicProcessingFitler is meant to be
		// earlier in the filter
		// chain than AnonymousAuthenticationFilter. Nevertheless, presence of both an
		// AnonymousAuthenticationToken
		// together with a BASIC authentication request header should indicate
		// reauthentication using the
		// BASIC protocol is desirable. This behaviour is also consistent with that
		// provided by form and digest,
		// both of which force re-authentication if the respective header is detected
		// (and
		// in doing so replace
		// any existing AnonymousAuthenticationToken). See SEC-610.
		if (existingAuth instanceof AnonymousAuthenticationToken) {
			return true;
		}

		return false;
	}

	protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			Authentication authResult) throws IOException {
	}

	protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException {
	}

	protected AuthenticationEntryPoint getAuthenticationEntryPoint() {
		return this.authenticationEntryPoint;
	}

	protected boolean isIgnoreFailure() {
		return this.ignoreFailure;
	}

	public void setAuthenticationDetailsSource(
			AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
		Assert.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
		this.authenticationDetailsSource = authenticationDetailsSource;
	}

	protected String getTokenParameter() {
		return tokenParameter;
	}

	public void setTokenParameter(String tokenParameter) {
		Assert.notNull(tokenParameter, "tokenParameter cannot be null");
		this.tokenParameter = tokenParameter;
	}

	public SceneService getSceneService() {
		return sceneService;
	}

	public void setSceneService(SceneService sceneService) {
		Assert.notNull(sceneService, "tokenParameter cannot be null");
		this.sceneService = sceneService;
	}

}