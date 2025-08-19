package com.codemages.Moviee.security.password;

import com.codemages.Moviee.security.password.interfaces.PasswordValidator;
import org.springframework.stereotype.Component;

import static com.codemages.Moviee.security.password.PasswordGeneratorConstants.*;

@Component
public class PasswordPolicyValidator implements PasswordValidator {
  @Override
  public boolean isValid(String password) {
    if ( password == null || password.length() < MIN_LENGTH || password.length() > MAX_LENGTH ) {
      return false;
    }

    boolean hasUpper = password.chars().anyMatch( Character::isUpperCase );
    boolean hasLower = password.chars().anyMatch( Character::isLowerCase );
    boolean hasDigit = password.chars().anyMatch( Character::isDigit );
    boolean hasSpecial = password.chars().anyMatch( ch -> SPECIAL.indexOf( ch ) >= 0 );

    return hasUpper && hasLower && hasDigit && hasSpecial;
  }
}
