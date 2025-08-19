package com.codemages.Moviee.security.config;

import com.codemages.Moviee.client.Client;
import com.codemages.Moviee.client.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Set;
import java.util.UUID;

@Repository
@Primary
@RequiredArgsConstructor
public class CustomRegisteredClientRepository
  implements RegisteredClientRepository {

  private final ClientRepository clientRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  @Override
  public void save(RegisteredClient registeredClient) {
    Client client = clientRepository.findByClientId( registeredClient.getClientId() )
      .orElseGet( Client::new );

    if ( client.getId() == null && registeredClient.getId() != null ) {
      client.setId( UUID.fromString( registeredClient.getId() ) );
    }
    client.setClientId( registeredClient.getClientId() );
    if ( registeredClient.getClientSecret() != null ) {
      client.setClientSecret( passwordEncoder.encode( registeredClient.getClientSecret() ) );
    }
    client.setClientName( registeredClient.getClientName() );
    client.setRedirectUri( String.join( ",", registeredClient.getRedirectUris() ) );

    clientRepository.save( client );
  }

  @Transactional(readOnly = true)
  @Override
  @Nullable
  public RegisteredClient findById(String id) {
    var client = clientRepository.findById( UUID.fromString( id ) );
    return client.map( this::toRegisteredClient ).orElse( null );
  }

  @Transactional(readOnly = true)
  @Override
  @Nullable
  public RegisteredClient findByClientId(String clientId) {
    var client = clientRepository.findByClientId( clientId );
    return client.map( this::toRegisteredClient ).orElse( null );
  }

  private RegisteredClient toRegisteredClient(Client client) {
    return RegisteredClient.withId( client.getId().toString() )
      .clientId( client.getClientId() )
      .clientSecret( client.getClientSecret() )
      .clientName( client.getClientName() )
      .clientAuthenticationMethods( authMethods -> {
        authMethods.add( ClientAuthenticationMethod.CLIENT_SECRET_BASIC );
        authMethods.add( ClientAuthenticationMethod.CLIENT_SECRET_POST );
      } )
      .authorizationGrantTypes( grantTypes -> {
        grantTypes.add( AuthorizationGrantType.AUTHORIZATION_CODE );
        grantTypes.add( AuthorizationGrantType.REFRESH_TOKEN );
      } )
      .redirectUris( uris -> {
        if ( client.getRedirectUri() != null && !client.getRedirectUri().isEmpty() ) {
          uris.addAll( Set.of( client.getRedirectUri().split( "," ) ) );
        }
      } )
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
