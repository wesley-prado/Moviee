package com.codemages.Moviee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class AuthorizationServerConfig {
  private static final String CUSTOM_CONSENT_PAGE_URI = "/oauth2/consent";
  private static final String CUSTOM_LOGIN_PAGE_URI = "/login";

  @Bean
  @Order(1)
  SecurityFilterChain authServerFilterChain(HttpSecurity http)
    throws Exception {
    OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
      OAuth2AuthorizationServerConfigurer
        .authorizationServer();
    RequestMatcher endpointsMatcher = authorizationServerConfigurer
      .getEndpointsMatcher();

    authorizationServerConfigurer.oidc( withDefaults() ).authorizationEndpoint(
      auth -> auth.consentPage( CUSTOM_CONSENT_PAGE_URI ) );

    http.securityMatcher( endpointsMatcher )
      .authorizeHttpRequests( authz -> authz.anyRequest().authenticated() )
      .csrf( csrf -> csrf.ignoringRequestMatchers( endpointsMatcher ) )
      .with( authorizationServerConfigurer, withDefaults() );

    http.exceptionHandling(
      exceptions -> exceptions.defaultAuthenticationEntryPointFor(
        new LoginUrlAuthenticationEntryPoint( CUSTOM_LOGIN_PAGE_URI ),
        new MediaTypeRequestMatcher( MediaType.TEXT_HTML )
      ) );

    return http.build();
  }

  @Bean
  AuthorizationServerSettings authorizationServerSettings() {
    return AuthorizationServerSettings.builder().issuer( "http://localhost:8080" )
      .build();
  }
}
