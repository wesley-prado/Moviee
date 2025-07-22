package com.codemages.Moviee.config;

import com.codemages.Moviee.config.properties.SecurityProperties;
import com.codemages.Moviee.security.CustomJwtGrantedAuthoritiesConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
@RequiredArgsConstructor
public class ResourceServerConfig {
  private final SecurityProperties securityProperties;

  private static final String CUSTOM_LOGIN_PAGE_URI = "/login";

  @Bean
  @Order(2)
  SecurityFilterChain resourceServerFilterChain(HttpSecurity http)
    throws Exception {

    http.cors( withDefaults() )
      .csrf( csrf -> csrf.ignoringRequestMatchers( "/api/**", "/explorer/**" ) );

    http.authorizeHttpRequests( auth -> auth
      .requestMatchers( CUSTOM_LOGIN_PAGE_URI, "/error/**", "/api/public/**" )
      .permitAll()
      .requestMatchers( HttpMethod.POST, "/api/v1/users" )
      .permitAll()
      .requestMatchers( "/explorer/**" ).hasAuthority( "ADMIN" ).anyRequest()
      .authenticated() );

    http.oauth2ResourceServer( oauth2 -> oauth2.jwt(
      jwt -> jwt.jwtAuthenticationConverter( jwtAuthenticationConverter() ) ) );

    Integer oneDayInSeconds = 60 * 60 * 24;
    http.formLogin( form -> form.loginPage( CUSTOM_LOGIN_PAGE_URI )
        .failureUrl( CUSTOM_LOGIN_PAGE_URI + "?error=true" ).permitAll() )
      .logout( logout -> logout.logoutUrl( "/logout" )
        .logoutSuccessUrl( CUSTOM_LOGIN_PAGE_URI ).invalidateHttpSession( true )
        .permitAll().clearAuthentication( true ) )
      .rememberMe( rememberMe -> rememberMe.key( securityProperties.rememberMeKey() )
        .tokenValiditySeconds( oneDayInSeconds ) );


    return http.build();
  }

  private JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
      new CustomJwtGrantedAuthoritiesConverter()
        .jwtGrantedAuthoritiesConverter() );

    return jwtAuthenticationConverter;
  }
}
