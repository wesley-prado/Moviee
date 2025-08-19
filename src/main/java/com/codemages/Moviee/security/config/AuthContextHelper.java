package com.codemages.Moviee.security.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthContextHelper {
	public boolean isUserAdmin() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		return auth != null && auth.getAuthorities().stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority()
						.equalsIgnoreCase("ADMIN"));
	}
}
