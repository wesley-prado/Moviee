package com.codemages.Moviee.auth.security.factory;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.UUID;

public class RegisteredClientFactory {
  public static RegisteredClient createValidClient() {
    return RegisteredClient.withId( UUID.randomUUID().toString() )
      .clientId( UUID.randomUUID().toString() )
      .clientSecret( "{noop}client_secret" )
      .clientName( "Test Client" )
      .clientAuthenticationMethods( authMethods -> {
        authMethods.add( ClientAuthenticationMethod.CLIENT_SECRET_BASIC );
        authMethods.add( ClientAuthenticationMethod.CLIENT_SECRET_POST );
      } )
      .authorizationGrantTypes( grantTypes -> {
        grantTypes.add( AuthorizationGrantType.AUTHORIZATION_CODE );
        grantTypes.add( AuthorizationGrantType.REFRESH_TOKEN );
      } )
      .redirectUri( "https://moviee.test.com/callback" )
      .scopes( scopes -> {
        scopes.add( OidcScopes.OPENID );
        scopes.add( OidcScopes.PROFILE );
        scopes.add( OidcScopes.EMAIL );
        scopes.add( "offline_access" );
      } )
      .clientSettings( ClientSettings.builder()
        .requireAuthorizationConsent( true )
        .requireProofKey( false )
        .build() )
      .tokenSettings( TokenSettings.builder()
        .accessTokenTimeToLive( Duration.ofMinutes( 30 ) )
        .refreshTokenTimeToLive( Duration.ofDays( 7 ) )
        .build() )
      .build();
  }
}
