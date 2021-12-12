package com.matchus.global.config;

import com.matchus.domains.user.service.AuthService;
import com.matchus.global.jwt.Jwt;
import com.matchus.global.jwt.JwtAuthenticationFilter;
import com.matchus.global.jwt.JwtAuthenticationProvider;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final JwtConfig jwtConfig;

	public WebSecurityConfig(
		JwtConfig jwtConfig
	) {
		this.jwtConfig = jwtConfig;
	}

	@Override
	public void configure(WebSecurity web) {
		web
			.ignoring()
			.antMatchers(
				"/h2-console/**",
				"/v2/api-docs",
				"/configuration/ui",
				"/swagger-resources/**",
				"/configuration/security",
				"/swagger-ui.html",
				"/webjars/**"
			);
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return (request, response, e) -> {
			Authentication authentication = SecurityContextHolder
				.getContext()
				.getAuthentication();
			Object principal = authentication != null ? authentication.getPrincipal() : null;
			log.warn("{} is denied", principal, e);
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setContentType("text/plain;charset=UTF-8");
			response
				.getWriter()
				.write("ACCESS DENIED");
			response
				.getWriter()
				.flush();
			response
				.getWriter()
				.close();
		};
	}

	@Bean
	public Jwt jwt() throws UnsupportedEncodingException {
		return new Jwt(
			jwtConfig.getIssuer(),
			jwtConfig.getClientSecret(),
			jwtConfig.getExpirySeconds()
		);
	}

	@Bean
	public JwtAuthenticationProvider jwtAuthenticationProvider(
		AuthService authService,
		Jwt jwt
	) {
		return new JwtAuthenticationProvider(jwt, authService);
	}

	@Autowired
	public void configureAuthentication(
		AuthenticationManagerBuilder builder,
		JwtAuthenticationProvider authenticationProvider
	) {
		builder.authenticationProvider(authenticationProvider);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		Jwt jwt = getApplicationContext().getBean(Jwt.class);
		return new JwtAuthenticationFilter(jwtConfig.getHeader(), jwt);
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.addAllowedOrigin("http://localhost:9000");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic()
			.disable()
			.cors()
			.and()
			.authorizeRequests()
			.antMatchers("/users/me")
			.hasAnyRole("USER", "ADMIN")
			.anyRequest()
			.permitAll()
			.and()
			.formLogin()
			.disable()
			.csrf()
			.disable()
			.headers()
			.disable()
			.rememberMe()
			.disable()
			.logout()
			.disable()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.exceptionHandling()
			.accessDeniedHandler(accessDeniedHandler())
			.and()
			.addFilterAfter(jwtAuthenticationFilter(), SecurityContextPersistenceFilter.class)
		;
	}

}
