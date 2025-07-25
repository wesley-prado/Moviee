package com.codemages.Moviee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class RoleHierarchyConfig {

	@Bean
	RoleHierarchy roleHierarchy() {
		String hierarchy = "ADMIN > MODERATOR \n MODERATOR > USER";
		return RoleHierarchyImpl.fromHierarchy(hierarchy);
	}

	@Bean
	MethodSecurityExpressionHandler methodSecurityExpressionHandler(
			RoleHierarchy roleHierarchy) {
		DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
		expressionHandler.setRoleHierarchy(roleHierarchy);

		return expressionHandler;
	}
}
