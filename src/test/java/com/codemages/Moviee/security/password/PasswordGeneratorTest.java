package com.codemages.Moviee.security.password;

import com.codemages.Moviee.security.password.interfaces.PasswordGenerator;
import com.codemages.Moviee.security.password.interfaces.PasswordValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordGeneratorTest {
  private final PasswordGenerator passwordGenerator = new CustomPasswordGenerator();
  private final PasswordValidator passwordValidator = new PasswordPolicyValidator();

  @Nested
  @DisplayName("Testes para o método generate()")
  class GenerateTests {

    @RepeatedTest(100)
    @DisplayName("Deve sempre gerar uma senha que passe na validação do isValid")
    void generate_shouldAlwaysCreateAValidPassword() {
      String password = passwordGenerator.generate();
      assertTrue(
        passwordValidator.isValid( password ),
        "A senha gerada '" + password + "' deveria ser sempre válida"
      );
    }
  }
}
