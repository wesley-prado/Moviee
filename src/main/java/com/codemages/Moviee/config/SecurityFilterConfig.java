package com.codemages.moviee.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
public class SecurityFilterConfig {
	@Bean
	@Order(1)
	SecurityFilterChain authServSecurityFilterChain(HttpSecurity http)
			throws Exception {
		OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer
				.authorizationServer();
		RequestMatcher endpointsMatcher = authorizationServerConfigurer
				.getEndpointsMatcher();

		authorizationServerConfigurer
				.oidc(oidc -> oidc.providerConfigurationEndpoint(withDefaults())
						.clientRegistrationEndpoint(withDefaults())
						.userInfoEndpoint(withDefaults()))
				.tokenEndpoint(withDefaults());

		http.securityMatcher(endpointsMatcher)
				.authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
				.csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
				.with(authorizationServerConfigurer, withDefaults());

		http.exceptionHandling(
				exceptions -> exceptions.defaultAuthenticationEntryPointFor(
						new LoginUrlAuthenticationEntryPoint("/login"),
						new MediaTypeRequestMatcher(MediaType.TEXT_HTML)));

		return http.build();
	}

	@Bean
	@Order(2)
	SecurityFilterChain resourceServerSecurityFilterChain(HttpSecurity http)
			throws Exception {
		http.authorizeHttpRequests(
				authz -> authz.requestMatchers("/login").permitAll())
				.securityMatcher("/userinfo", "/api/**")
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/userinfo").hasAuthority("SCOPE_openid")
						.requestMatchers("/api/**").authenticated())
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults())).formLogin(
						form -> form.loginPage("/login").defaultSuccessUrl("/home", true));

		return http.build();
	}

	@Bean
	@Order(3)
	SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.headers(headers -> headers.frameOptions(fo -> fo.disable()));

		return http.build();
	}

	@Bean
	JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		grantedAuthoritiesConverter.setAuthorityPrefix("SCOPE_");

		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter
				.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
		return jwtAuthenticationConverter;
	}
}
