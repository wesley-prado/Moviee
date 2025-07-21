package com.codemages.Moviee.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "moviee.security")
public record SecurityProperties(String rememberMeKey) {

  public SecurityProperties {
    if ( rememberMeKey == null || rememberMeKey.isBlank() ) {
      throw new IllegalArgumentException( "Remember Me Key must not be null or blank" );
    }
  }
}
