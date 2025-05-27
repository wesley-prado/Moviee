package com.codemages.moviee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.codemages.moviee.security.CustomJwtGrantedAuthoritiesConverter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ResourceServerConfig {
	private static final String CUSTOM_LOGIN_PAGE_URI = "/login";

	@Bean
	@Order(2)
	SecurityFilterChain resourceServerFilterChain(HttpSecurity http)
			throws Exception {

		http.cors(withDefaults())
				.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**", "/explorer/**"));

		http.authorizeHttpRequests(auth -> auth
				.requestMatchers(CUSTOM_LOGIN_PAGE_URI, "/error/**", "/api/public/**")
				.permitAll()
				.requestMatchers(HttpMethod.POST, "/api/v1/users")
				.permitAll()
				.requestMatchers("/explorer/**").hasAuthority("ADMIN").anyRequest()
				.permitAll());
		// .anyRequest().authenticated()

		http.oauth2ResourceServer(oauth2 -> oauth2.jwt(
				jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

		http.formLogin(form -> form.loginPage(CUSTOM_LOGIN_PAGE_URI)
				.failureUrl(CUSTOM_LOGIN_PAGE_URI + "?error=true").permitAll())
				.logout(logout -> logout.logoutUrl("/logout")
						.logoutSuccessUrl(CUSTOM_LOGIN_PAGE_URI).invalidateHttpSession(true)
						.permitAll().clearAuthentication(true));

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
