package com.codemages.moviee.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityFilterConfig {
  @Bean
  @Order(1)
  SecurityFilterChain authServSecurityFilterChain(HttpSecurity http)
      throws Exception {
    OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer
        .authorizationServer();
    RequestMatcher endpointsMatcher = authorizationServerConfigurer
        .getEndpointsMatcher();

    http.securityMatcher(endpointsMatcher)
        .authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
        .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
        .with(authorizationServerConfigurer, withDefaults());

    authorizationServerConfigurer
        .oidc(oidc -> oidc.providerConfigurationEndpoint(withDefaults())
            .clientRegistrationEndpoint(withDefaults())
            .userInfoEndpoint(withDefaults()));

    http.exceptionHandling(
        exceptions -> exceptions.defaultAuthenticationEntryPointFor(
            new LoginUrlAuthenticationEntryPoint("/login"),
            new MediaTypeRequestMatcher(MediaType.TEXT_HTML)));

    return http.build();
  }

  @Bean
  @Order(2)
  SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
        .formLogin(withDefaults()).csrf(csrf -> csrf.disable())
        .headers(headers -> headers.frameOptions(fo -> fo.disable()));

    return http.build();
  }
}
