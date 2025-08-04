package com.codemages.Moviee.security;

import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;

public class CustomTokenSettings {
  public TokenSettings getSettings() {
    return TokenSettings.builder().accessTokenTimeToLive( Duration.ofMinutes( 30 ) )
      .refreshTokenTimeToLive( Duration.ofDays( 7 ) ).reuseRefreshTokens( true )
      .build();
  }
}
