package com.codemages.moviee.utils.validators;

import org.springframework.context.support.BeanDefinitionDsl.Role;

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
			return false;
		}
	}
}
