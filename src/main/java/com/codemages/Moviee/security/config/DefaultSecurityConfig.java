package com.codemages.Moviee.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class DefaultSecurityConfig {

  @Bean
  @Order(2)
  public SecurityFilterChain defaultSecurityFilterChain(
    HttpSecurity http,
    DataSource dataSource
  ) throws Exception {
    http
      .authorizeHttpRequests( authorize -> authorize
        .requestMatchers( "/auth/login", "/css/**", "/js/**" ).permitAll()
        .anyRequest().authenticated()
      ).csrf( csrf -> csrf.ignoringRequestMatchers( "/auth/login" ) )
      .formLogin( formLogin ->
        formLogin.loginPage( "/auth/login" )
      )
      .rememberMe( rememberMe ->
        rememberMe.tokenRepository( persistentTokenRepository( dataSource ) )
      ).logout( logout ->
        logout.logoutSuccessUrl( "/auth/login?logout" )
          .deleteCookies( "JSESSIONID", "remember-me" )
          .invalidateHttpSession( true )
          .clearAuthentication( true ) );

    return http.build();
  }

  @Bean
  public PersistentTokenRepository persistentTokenRepository(DataSource dataSource) {
    JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
    tokenRepository.setDataSource( dataSource );

    return tokenRepository;
  }
}