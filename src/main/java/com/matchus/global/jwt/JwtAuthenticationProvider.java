package com.matchus.global.jwt;

import static org.apache.commons.lang3.ClassUtils.*;

import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.service.UserService;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationProvider implements AuthenticationProvider {

	private final Jwt jwt;

	private final UserService userService;

	public JwtAuthenticationProvider(Jwt jwt, UserService userService) {
		this.jwt = jwt;
		this.userService = userService;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return isAssignable(JwtAuthenticationToken.class, authentication);
	}

	@Override
	public Authentication authenticate(Authentication authentication)
		throws AuthenticationException {
		JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken) authentication;
		return processUserAuthentication(
			String.valueOf(jwtAuthentication.getPrincipal()),
			jwtAuthentication.getCredentials()
		);
	}

	private Authentication processUserAuthentication(String principal, String credentials) {
		try {
			User user = userService.loadUserByUserEmail(principal, credentials);
			List<GrantedAuthority> authorities = user
				.getGroup()
				.getAuthorities();
			String token = getToken(user.getEmail(), authorities);
			JwtAuthenticationToken authenticated =
				new JwtAuthenticationToken(
					new JwtAuthentication(token, user.getEmail()), null, authorities);
			authenticated.setDetails(user);
			return authenticated;
		} catch (IllegalArgumentException e) {
			throw new BadCredentialsException(e.getMessage());
		} catch (DataAccessException e) {
			throw new AuthenticationServiceException(e.getMessage(), e);
		}
	}

	private String getToken(String username, List<GrantedAuthority> authorities) {
		String[] roles = authorities
			.stream()
			.map(GrantedAuthority::getAuthority)
			.toArray(String[]::new);
		return jwt.sign(Jwt.Claims.from(username, roles));
	}

}
