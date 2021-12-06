package com.matchus.global.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

public class JwtAuthenticationFilter extends GenericFilterBean {

	private static final String KEY = "token";
	private static final String BEARER_TYPE = "Bearer";

	private final JwtTokenProvider jwtTokenProvider;
	private final RedisTemplate redisTemplate;

	public JwtAuthenticationFilter(
		JwtTokenProvider jwtTokenProvider,
		RedisTemplate redisTemplate
	) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {

		String token = resolveToken((HttpServletRequest) request);

		if (token != null && jwtTokenProvider.validateToken(token)) {
			String isLogout = (String) redisTemplate
				.opsForValue()
				.get(token);
			if (ObjectUtils.isEmpty(isLogout)) {
				Authentication authentication = jwtTokenProvider.getAuthentication(token);
				SecurityContextHolder
					.getContext()
					.setAuthentication(authentication);
			}
		}
		chain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(KEY);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
			return bearerToken.substring(7);
		}
		return null;
	}
}