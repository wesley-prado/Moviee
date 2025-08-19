package com.codemages.Moviee.security.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "moviee.security")
public record SecurityProperties(String rememberMeKey, String issuerUri) {

  public SecurityProperties {
    if ( rememberMeKey == null || rememberMeKey.isBlank() ) {
      throw new IllegalArgumentException( "Remember Me Key must not be null or blank" );
    }

    if ( issuerUri == null || issuerUri.isBlank() ) {
      throw new IllegalArgumentException( "Issuer URI must not be null or blank" );
    }
  }
}
