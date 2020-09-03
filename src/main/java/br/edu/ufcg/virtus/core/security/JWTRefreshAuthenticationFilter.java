package br.edu.ufcg.virtus.core.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.edu.ufcg.virtus.core.config.AppContext;
import br.edu.ufcg.virtus.core.dto.LoggedUserDTO;
import br.edu.ufcg.virtus.core.util.JSonUtil;

/**
 * Filter for Refresh Authentication.
 *
 * @author Virtus
 *
 */
public class JWTRefreshAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	/**
	 * Constructor.
	 *
	 * @param url URL.
	 */
	public JWTRefreshAuthenticationFilter(String url) {
		super(new AntPathRequestMatcher(url));
		
		this.setAuthenticationManager(new JWTAuthenticationManager());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter#attemptAuthentication(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		response = SecurityUtils.fillAccessControlHeader(response);
		
		return getTokenService().getRefreshAuthetication(request);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter#successfulAuthentication(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain,
	 *      org.springframework.security.core.Authentication)
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		LoggedUserDTO dto = getTokenService().addAuthentication((LoggedUserDTO) auth.getPrincipal());
		dto = getManager().addPermissions(dto, dto.getIdRole());
		
		response.setHeader("Content-Type", "application/json");
		response.getWriter().write(JSonUtil.toJSon(dto));
	}

	/**
	 * Gets the token service.
	 * 
	 * @return Token Service.
	 */
	private TokenAuthenticationService getTokenService() {
		return AppContext.getBean(TokenAuthenticationService.class);
	}
	
	/**
	 * Gets the JWT Authentication Manager.
	 * 
	 * @return JWT Authentication Manager.
	 */
	private JWTAuthenticationManager getManager() {
		return (JWTAuthenticationManager) getAuthenticationManager();
	}
}
