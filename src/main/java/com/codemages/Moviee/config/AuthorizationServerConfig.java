package com.codemages.Moviee.config;

import com.codemages.Moviee.config.properties.SecurityProperties;
import com.codemages.Moviee.security.PublicClientRefreshTokenAuthenticationConverter;
import com.codemages.Moviee.security.PublicClientRefreshTokenAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties(SecurityProperties.class)
public class AuthorizationServerConfig {
  private final RegisteredClientRepository registeredClientRepository;
  private final SecurityProperties securityProperties;

  private static final String CUSTOM_CONSENT_PAGE_URI = "/oauth2/consent";
  private static final String CUSTOM_LOGIN_PAGE_URI = "/login";

  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  SecurityFilterChain authServerFilterChain(
    HttpSecurity http,
    OAuth2AuthorizationService authorizationService
  ) throws Exception {
    var authServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer();

    authServerConfigurer.oidc( Customizer.withDefaults() )
      .authorizationEndpoint( endpoint -> endpoint.consentPage( CUSTOM_CONSENT_PAGE_URI ) );

    RequestMatcher endpointsMatcher = authServerConfigurer.getEndpointsMatcher();

    http.securityMatcher( endpointsMatcher )
      .authorizeHttpRequests( (authorize) -> authorize.anyRequest().authenticated() )
      .csrf( csrf -> csrf.ignoringRequestMatchers( endpointsMatcher ) )
      .exceptionHandling( exceptions -> exceptions.authenticationEntryPoint( new LoginUrlAuthenticationEntryPoint(
        CUSTOM_LOGIN_PAGE_URI ) ) )
      .with(
        authServerConfigurer,
        authServer -> authServer.clientAuthentication( oAuth2ClientAuthenticationConfigurer -> oAuth2ClientAuthenticationConfigurer.authenticationProvider(
            new PublicClientRefreshTokenAuthenticationProvider(
              registeredClientRepository,
              authorizationService
            ) )
          .authenticationConverter( new PublicClientRefreshTokenAuthenticationConverter() ) )
      );

    return http.build();
  }

  @Bean
  AuthorizationServerSettings authorizationServerSettings() {
    return AuthorizationServerSettings.builder().issuer( securityProperties.issuerUri() ).build();
  }

  @Bean
  public OAuth2AuthorizationService authorizationService(
    JdbcOperations jdbcOperations,
    RegisteredClientRepository registeredClientRepository
  ) {
    return new JdbcOAuth2AuthorizationService( jdbcOperations, registeredClientRepository );
  }
}
