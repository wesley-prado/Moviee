package com.codemages.Moviee.utils.password;

import com.codemages.Moviee.utils.password.interfaces.PasswordValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordValidatorTest {
  private final PasswordValidator passwordValidator = new PasswordPolicyValidator();

  @Nested
  @DisplayName("Testes para o método isValid()")
  class IsValidTests {

    @Test
    @DisplayName("Deve retornar true para uma senha válida")
    void isValid_shouldReturnTrue_forValidPassword() {
      assertTrue( passwordValidator.isValid( "ValidPass123!" ) );
    }

    @Test
    @DisplayName("Deve retornar false para senha muito curta")
    void isValid_shouldReturnFalse_whenPasswordIsTooShort() {
      assertFalse(
        passwordValidator.isValid( "Sh0rt!" ),
        "A senha 'Sh0rt!' deveria ser inválida (muito curta)"
      );
    }

    @Test
    @DisplayName("Deve retornar false para senha muito longa")
    void isValid_shouldReturnFalse_whenPasswordIsTooLong() {
      assertFalse(
        passwordValidator.isValid( "ThisPasswordIsWayTooLong1!" ),
        "A senha deveria ser inválida (muito longa)"
      );
    }

    @Test
    @DisplayName("Deve retornar false para senha sem letra maiúscula")
    void isValid_shouldReturnFalse_whenMissingUppercase() {
      assertFalse(
        passwordValidator.isValid( "nouppercase1!" ),
        "A senha deveria ser inválida (falta maiúscula)"
      );
    }

    @Test
    @DisplayName("Deve retornar false para senha sem letra minúscula")
    void isValid_shouldReturnFalse_whenMissingLowercase() {
      assertFalse(
        passwordValidator.isValid( "NOLOWERCASE1!" ),
        "A senha deveria ser inválida (falta minúscula)"
      );
    }

    @Test
    @DisplayName("Deve retornar false para senha sem número")
    void isValid_shouldReturnFalse_whenMissingDigit() {
      assertFalse(
        passwordValidator.isValid( "NoDigitPass!" ),
        "A senha deveria ser inválida (falta número)"
      );
    }

    @Test
    @DisplayName("Deve retornar false para senha sem caractere especial")
    void isValid_shouldReturnFalse_whenMissingSpecialChar() {
      assertFalse(
        passwordValidator.isValid( "NoSpecial123" ),
        "A senha deveria ser inválida (falta caractere especial)"
      );
    }

    @Test
    @DisplayName("Deve retornar false para senha nula ou em branco")
    void isValid_shouldReturnFalse_whenPasswordIsBlank() {
      assertFalse( passwordValidator.isValid( null ), "Senha nula deveria ser inválida" );
      assertFalse( passwordValidator.isValid( "" ), "Senha vazia deveria ser inválida" );
      assertFalse( passwordValidator.isValid( "   " ), "Senha em branco deveria ser inválida" );
    }
  }

}
