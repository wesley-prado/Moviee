package com.codemages.moviee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.codemages.moviee.security.CustomJwtGrantedAuthoritiesConverter;

@Configuration
public class ResourceServerConfig {
	private static final String CUSTOM_LOGIN_PAGE_URI = "/login";

	@Bean
	@Order(2)
	SecurityFilterChain resourceServerFilterChain(HttpSecurity http)
			throws Exception {
		http.authorizeHttpRequests(
				auth -> auth.requestMatchers(CUSTOM_LOGIN_PAGE_URI, "/error/**")
						.permitAll().anyRequest().authenticated())
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt
						.jwtAuthenticationConverter(jwtAuthenticationConverter())))
				.formLogin(form -> form.loginPage(CUSTOM_LOGIN_PAGE_URI));
		return http.build();
	}

	private JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
				new CustomJwtGrantedAuthoritiesConverter()
						.jwtGrantedAuthoritiesConverter());

		return jwtAuthenticationConverter;
	}
}
