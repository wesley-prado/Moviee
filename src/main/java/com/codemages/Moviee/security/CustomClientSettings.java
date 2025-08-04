package com.codemages.Moviee.security;

import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

public class CustomClientSettings {
  public ClientSettings getClientSettings() {
    return ClientSettings.builder().requireAuthorizationConsent( true )
      .requireProofKey( true ).build();
  }
}
