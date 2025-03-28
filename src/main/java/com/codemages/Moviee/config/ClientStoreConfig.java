package com.codemages.moviee.config;

import java.time.Duration;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

@Configuration
public class ClientStoreConfig {
	@Bean
	RegisteredClientRepository registeredClientRepository() {
		RegisteredClient registeredClient = RegisteredClient
				.withId(UUID.randomUUID().toString()).clientId("my_client_id")
				.clientSecret("{noop}my_client_secret")
				.clientAuthenticationMethods(s -> {
					s.add(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
					s.add(ClientAuthenticationMethod.CLIENT_SECRET_POST);
				}).authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.authorizationGrantType(AuthorizationGrantType.JWT_BEARER)
				.redirectUri("http://127.0.0.1:8080/login/oauth2/code/client-server")
				.scope(OidcScopes.OPENID).scope(OidcScopes.PROFILE)
				.scope("offline_access")
				.clientSettings(ClientSettings.builder()
						.requireAuthorizationConsent(true).requireProofKey(true).build())
				.tokenSettings(TokenSettings.builder()
						.accessTokenTimeToLive(Duration.ofMinutes(30))
						.refreshTokenTimeToLive(Duration.ofMinutes(30))
						.reuseRefreshTokens(true).build())
				.build();

		return new InMemoryRegisteredClientRepository(registeredClient);
	}
}
