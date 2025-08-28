package com.codemages.Moviee.auth.security.password;

public interface PasswordValidator {
  /**
   * Validates the generated password against the strong password criteria.
   *
   * @param password the password to validate.
   *
   * @return true if the password is valid, false otherwise.
   */
  boolean isValid(String password);
}
