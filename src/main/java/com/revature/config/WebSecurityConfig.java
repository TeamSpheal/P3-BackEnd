package com.revature.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {
    @Value("${teamspeal.frontend.url}")
	private String frontendURL;
    
    @Bean
	public WebMvcConfigurer corsConfig() {
		
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
					.allowedOrigins(frontendURL)
					.allowedHeaders("*")
					.exposedHeaders("Auth", "ResetToken")
					.allowCredentials(true);
			}
		};
	}
}
