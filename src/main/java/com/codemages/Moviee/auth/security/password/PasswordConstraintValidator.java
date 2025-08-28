package com.codemages.Moviee.auth.security.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("passwordConstraintValidator")
@RequiredArgsConstructor
public class PasswordConstraintValidator implements ConstraintValidator<StrongPassword, String> {
  private final PasswordValidator passwordValidator;

  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    return passwordValidator.isValid( password );
  }
}
