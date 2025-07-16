package com.codemages.Moviee.utils.password;

import com.codemages.Moviee.utils.password.interfaces.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component("passwordConstraintValidator")
@RequiredArgsConstructor
public class PasswordConstraintValidator implements ConstraintValidator<StrongPassword, String> {
  private final PasswordValidator passwordValidator;

  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    return passwordValidator.isValid( password );
  }
}
