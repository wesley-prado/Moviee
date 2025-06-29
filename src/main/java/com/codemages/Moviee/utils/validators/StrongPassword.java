package com.codemages.Moviee.utils.validators;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({
		FIELD, PARAMETER
})
@Retention(RUNTIME)
public @interface StrongPassword {
	String message() default "Password must be 8-20 characters and include uppercase, lowercase, digit, and special character";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
