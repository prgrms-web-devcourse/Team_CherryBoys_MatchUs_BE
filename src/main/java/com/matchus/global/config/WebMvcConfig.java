package com.matchus.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
			.addMapping("/**")
			.allowedOrigins(
				"http://localhost:9000", "https://matchus-develop.netlify.app",
				"https://matchus.netlify.app"
			)
			.allowedMethods("*")
			.allowedHeaders("*")
			.allowCredentials(true);
	}

}
