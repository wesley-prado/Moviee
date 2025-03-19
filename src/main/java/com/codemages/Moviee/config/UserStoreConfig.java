package com.codemages.moviee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserStoreConfig {

  @Bean
  UserDetailsService userDetailsService() {
    var userDetailsManager = new InMemoryUserDetailsManager();

    userDetailsManager.createUser(User.withUsername("wesleyprado")
        .password("{noop}12345").roles("ADMIN").build());

    return userDetailsManager;
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

}
