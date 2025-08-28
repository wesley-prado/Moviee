package com.codemages.Moviee.auth.security.password;

public interface PasswordGenerator {
  /**
   * Generates a strong password.
   *
   * @return a strong password as a String.
   */
  String generate();
}
