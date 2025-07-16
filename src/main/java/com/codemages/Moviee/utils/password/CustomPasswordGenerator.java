package com.codemages.Moviee.utils.password;

import com.codemages.Moviee.utils.password.interfaces.PasswordGenerator;
import com.codemages.Moviee.utils.password.interfaces.PasswordValidator;
import org.springframework.stereotype.Component;

import static com.codemages.Moviee.utils.password.PasswordGeneratorConstants.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class CustomPasswordGenerator implements PasswordGenerator {

  private static final SecureRandom random = new SecureRandom();

  @Override
  public String generate() {
    List<Character> passwordChars = new ArrayList<>();

    passwordChars.add( UPPER.charAt( random.nextInt( UPPER.length() ) ) );
    passwordChars.add( LOWER.charAt( random.nextInt( LOWER.length() ) ) );
    passwordChars.add( DIGITS.charAt( random.nextInt( DIGITS.length() ) ) );
    passwordChars.add( SPECIAL.charAt( random.nextInt( SPECIAL.length() ) ) );

    String allChars = LOWER + UPPER + DIGITS + SPECIAL;
    int remainingLength =
      MIN_LENGTH + random.nextInt( MAX_LENGTH - MIN_LENGTH + 1 ) - passwordChars.size();

    for (int i = 0; i < remainingLength; i++) {
      passwordChars.add( allChars.charAt( random.nextInt( allChars.length() ) ) );
    }

    Collections.shuffle( passwordChars, random );

    StringBuilder password = new StringBuilder();
    for (Character ch : passwordChars) {
      password.append( ch );
    }

    return password.toString();
  }
}
