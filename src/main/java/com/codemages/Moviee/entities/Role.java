package com.codemages.Moviee.entities;

import java.util.ArrayList;
import java.util.List;

public enum Role {
	USER,
	ADMIN,
	MODERATOR;

	public static List<String> getRoles() {
		List<String> roles = new ArrayList<>();

		for (Role role : Role.values()) {
			roles.add(role.name());
		}

		return roles;
	}
}
