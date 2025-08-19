package com.codemages.Moviee.security.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomJwtGrantedAuthoritiesConverter {
	public Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter() {
		return jwt -> {
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			List<String> scopes = jwt.getClaimAsStringList("scope");

			if (scopes != null) {
				for (String scope : scopes) {
					authorities.add(new SimpleGrantedAuthority("SCOPE_" + scope));
				}
			}

			List<String> roles = jwt.getClaimAsStringList("roles");

			if (roles != null) {
				for (String role : roles) {
					authorities.add(new SimpleGrantedAuthority(role));
				}
			}
			return authorities;
		};
	}
}
