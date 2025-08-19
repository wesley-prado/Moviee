package com.codemages.Moviee.security.config;

import com.codemages.Moviee.security.config.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableMethodSecurity
@EnableConfigurationProperties(SecurityProperties.class)
public class AuthorizationServerConfig {
  private static final String CONSENT_URL = "/auth/consent";

  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public SecurityFilterChain authServerFilterChain(HttpSecurity http) throws Exception {
    var authServerConfigurer = new OAuth2AuthorizationServerConfigurer();

    authServerConfigurer.oidc( Customizer.withDefaults() )
      .authorizationEndpoint( endpoint -> endpoint.consentPage( CONSENT_URL ) );

    http.securityMatcher( authServerConfigurer.getEndpointsMatcher() )
      .authorizeHttpRequests( authorize -> authorize.anyRequest().authenticated() )
      .csrf( csrf -> csrf.ignoringRequestMatchers( authServerConfigurer.getEndpointsMatcher() ) )
      .exceptionHandling( exceptions -> exceptions.authenticationEntryPoint( new LoginUrlAuthenticationEntryPoint(
        "/auth/login" ) ) )
      .with( authServerConfigurer, Customizer.withDefaults() );

    return http.build();
  }

  @Bean
  public AuthorizationServerSettings authorizationServerSettings(SecurityProperties securityProperties) {
    return AuthorizationServerSettings.builder().issuer( securityProperties.issuerUri() ).build();
  }
}
