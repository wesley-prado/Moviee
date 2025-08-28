package com.codemages.Moviee.user.validator.roles;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RoleValidator.class)
public @interface ValidRole {
	String message() default "Invalid user role.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
