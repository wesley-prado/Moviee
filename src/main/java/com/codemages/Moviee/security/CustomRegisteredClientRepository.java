package com.codemages.Moviee.security;

import com.codemages.Moviee.entities.Client;
import com.codemages.Moviee.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Repository
@Primary
@RequiredArgsConstructor
public class CustomRegisteredClientRepository
  implements RegisteredClientRepository {

  private final ClientRepository clientRepository;

  @Transactional
  @Override
  public void save(RegisteredClient registeredClient) {
    var client = new Client();
    client.setClientId( registeredClient.getClientId() );
    client.setClientName( registeredClient.getClientName() );
    client.setRedirectUri( String.join( ",", registeredClient.getRedirectUris() ) );

    clientRepository.save( client );
  }

  @Transactional(readOnly = true)
  @Override
  @Nullable
  public RegisteredClient findById(String id) {
    var client = clientRepository.findById( UUID.fromString( id ) );

    if ( client.isEmpty() ) {
      return null;
    }

    return toRegisteredClient( client.get() );
  }

  @Transactional(readOnly = true)
  @Override
  @Nullable
  public RegisteredClient findByClientId(String clientId) {
    var client = clientRepository.findByClientId( clientId );

    if ( client == null ) {
      return null;
    }

    return toRegisteredClient( client );
  }

  private RegisteredClient toRegisteredClient(Client client) {
    return RegisteredClient.withId( client.getId().toString() )
      .clientId( client.getClientId() )
      .clientName( client.getClientName() )
      .clientAuthenticationMethod( ClientAuthenticationMethod.NONE )
      .authorizationGrantType( AuthorizationGrantType.AUTHORIZATION_CODE )
      .authorizationGrantType( AuthorizationGrantType.REFRESH_TOKEN )
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
      .clientSettings( new CustomClientSettings().getClientSettings() )
      .tokenSettings( new CustomTokenSettings().getSettings() )
      .build();
  }
}
