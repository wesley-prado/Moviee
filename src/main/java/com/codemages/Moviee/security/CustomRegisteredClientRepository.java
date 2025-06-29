package com.codemages.Moviee.security;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import com.codemages.Moviee.entities.Client;
import com.codemages.Moviee.repositories.ClientRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomRegisteredClientRepository
		implements RegisteredClientRepository {
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void save(RegisteredClient registeredClient) {
		var client = new Client();
		client.setClientId(registeredClient.getClientId());
		String hashedSecret = passwordEncoder
				.encode(registeredClient.getClientSecret());
		client.setClientSecret(hashedSecret);
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
		Set<String> redirectUris = new HashSet<>();
		String redirectUriString = client.getRedirectUri();

		if (redirectUriString != null && !redirectUriString.isEmpty()) {
			redirectUriString = redirectUriString.replaceAll("[\\[\\]]", "");
			String[] redirectUriArray = redirectUriString.split(",");

			for (String uri : redirectUriArray) {
				redirectUris.add(uri.trim());
			}
		}

		return RegisteredClient.withId(client.getId().toString())
				.clientId(client.getClientId()).clientSecret(client.getClientSecret())
				.clientAuthenticationMethods(authMethods -> {
					authMethods.add(ClientAuthenticationMethod.CLIENT_SECRET_POST);
					authMethods.add(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);

				}).authorizationGrantTypes(authorizationGrantTypes -> {
					authorizationGrantTypes
							.add(AuthorizationGrantType.AUTHORIZATION_CODE);
					authorizationGrantTypes.add(AuthorizationGrantType.REFRESH_TOKEN);
					authorizationGrantTypes
							.add(AuthorizationGrantType.CLIENT_CREDENTIALS);
					authorizationGrantTypes.add(AuthorizationGrantType.JWT_BEARER);
				}).redirectUris(uris -> redirectUris.forEach(uris::add))
				.scopes(scopes -> {
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
