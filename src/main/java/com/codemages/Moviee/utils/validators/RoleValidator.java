package com.codemages.moviee.utils.validators;

import com.codemages.moviee.entities.Role;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<ValidRole, String> {

	@Override
	public boolean isValid(
			String role,
			ConstraintValidatorContext ctx) {
		try {
			Role.valueOf(role);
			return true;
		} catch (Exception e) {
			System.out.println("Invalid role: " + role);
			return false;
		}
	}
}
