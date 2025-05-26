package com.codemages.moviee.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthContextHelper {
	public void getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		System.out.println("Authentication: " + auth);
		System.out.println("Authentication name: " + auth.getName());

		Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
	}
}
