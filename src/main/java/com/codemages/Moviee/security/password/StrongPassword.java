package com.codemages.Moviee.security.password;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({
  FIELD, PARAMETER
})
@Retention(RUNTIME)
public @interface StrongPassword {
  String message() default "Password must be between 8 and 20 characters long, contain at least " +
    "one uppercase letter, one lowercase letter, one digit, and one special character.";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };
}
