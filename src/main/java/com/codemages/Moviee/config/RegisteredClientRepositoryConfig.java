package com.codemages.moviee.config;

import java.time.Duration;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import com.codemages.moviee.entities.Client;
import com.codemages.moviee.repositories.ClientRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RegisteredClientRepositoryConfig
		implements RegisteredClientRepository {
	@Autowired
	private ClientRepository clientRepository;

	// @Bean
	// RegisteredClientRepository registeredClientRepository() {
	// RegisteredClient registeredClient = RegisteredClient
	// .withId(UUID.randomUUID().toString()).clientId("my_client_id")
	// .clientSecret("{noop}my_client_secret")
	// .clientAuthenticationMethods(s -> {
	// s.add(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
	// s.add(ClientAuthenticationMethod.CLIENT_SECRET_POST);
	// }).authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
	// .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
	// .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
	// .authorizationGrantType(AuthorizationGrantType.JWT_BEARER)
	// .redirectUri("http://127.0.0.1:8080/login/oauth2/code/client-server")
	// .scope(OidcScopes.OPENID).scope(OidcScopes.EMAIL)
	// .clientSettings(ClientSettings.builder()
	// .requireAuthorizationConsent(true).requireProofKey(true).build())
	// .tokenSettings(TokenSettings.builder()
	// .accessTokenTimeToLive(Duration.ofMinutes(30))
	// .refreshTokenTimeToLive(Duration.ofMinutes(30))
	// .reuseRefreshTokens(true).build())
	// .build();

	// return new InMemoryRegisteredClientRepository(registeredClient);
	// }

	@Override
	public void save(RegisteredClient registeredClient) {
		var client = new Client();
		client.setClientId(registeredClient.getClientId());
		client.setClientSecret(registeredClient.getClientSecret());
		client.setRedirectUri(registeredClient.getRedirectUris().toString());

		clientRepository.save(client);
	}

	@Override
	@Nullable
	public RegisteredClient findById(String id) {
		var client = clientRepository.findById(UUID.fromString(id));

		if (client.isEmpty()) {
			return null;
		}

		return toRegisteredClient(client.get());
	}

	@Override
	@Nullable
	public RegisteredClient findByClientId(String clientId) {
		var client = clientRepository.findByClientId(clientId);

		if (client == null) {
			return null;
		}

		return toRegisteredClient(client);
	}

	private RegisteredClient toRegisteredClient(Client client) {
		return RegisteredClient.withId(client.getId().toString())
				.clientId(client.getClientId()).clientSecret(client.getClientSecret())
				.clientAuthenticationMethod(
						ClientAuthenticationMethod.CLIENT_SECRET_POST)
				.authorizationGrantTypes(authorizationGrantTypes -> {
					authorizationGrantTypes
							.add(AuthorizationGrantType.AUTHORIZATION_CODE);
					authorizationGrantTypes.add(AuthorizationGrantType.REFRESH_TOKEN);
					authorizationGrantTypes
							.add(AuthorizationGrantType.CLIENT_CREDENTIALS);
					authorizationGrantTypes.add(AuthorizationGrantType.JWT_BEARER);
				}).redirectUri(client.getRedirectUri()).scopes(scopes -> {
					scopes.add(OidcScopes.OPENID);
					scopes.add(OidcScopes.EMAIL);
				}).clientSettings(getClientSettings()).tokenSettings(getSettings())
				.build();
	}

	private ClientSettings getClientSettings() {
		return ClientSettings.builder().requireAuthorizationConsent(true)
				.requireProofKey(true).build();
	}

	private TokenSettings getSettings() {
		return TokenSettings.builder().accessTokenTimeToLive(Duration.ofMinutes(30))
				.refreshTokenTimeToLive(Duration.ofDays(7)).reuseRefreshTokens(true)
				.build();
	}
}
