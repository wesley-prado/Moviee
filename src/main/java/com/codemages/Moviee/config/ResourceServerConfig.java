package com.codemages.Moviee.config;

import com.codemages.Moviee.security.CustomJwtGrantedAuthoritiesConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class ResourceServerConfig {
  @Bean
  @Order(1)
  SecurityFilterChain resourceServerFilterChain(HttpSecurity http)
    throws Exception {

    http
      .securityMatcher( "/api/**", "/explorer/**" )
      .cors( withDefaults() )
      .csrf( csrf -> csrf.disable() )
      .authorizeHttpRequests( auth -> auth
        .requestMatchers( "/api/public/**" ).permitAll()
        .anyRequest().authenticated() )
      .oauth2ResourceServer( oauth2 -> oauth2.jwt(
        jwt -> jwt.jwtAuthenticationConverter( jwtAuthenticationConverter() ) ) );

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
