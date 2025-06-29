package com.codemages.Moviee.utils.validators;

import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component("passwordValidator")
public class PasswordValidator
		implements Validator<String>, ConstraintValidator<StrongPassword, String> {

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		return isValid(password);
	}

	@Override
	public boolean isValid(String password) {
		if (password == null || password.length() < 8) {
			return false;
		}

		boolean hasUpperCase = false;
		boolean hasLowerCase = false;
		boolean hasDigit = false;
		boolean hasSpecialChar = false;

		for (char c : password.toCharArray()) {
			if (Character.isUpperCase(c)) {
				hasUpperCase = true;
			} else if (Character.isLowerCase(c)) {
				hasLowerCase = true;
			} else if (Character.isDigit(c)) {
				hasDigit = true;
			} else if (!Character.isLetter(c)) {
				hasSpecialChar = true;
			}
		}

		return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
	}
}
