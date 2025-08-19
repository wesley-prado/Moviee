package com.codemages.Moviee.security.password.interfaces;

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
