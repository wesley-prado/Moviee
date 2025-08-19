package com.codemages.Moviee.security.password.interfaces;

public interface PasswordGenerator {
  /**
   * Generates a strong password.
   *
   * @return a strong password as a String.
   */
  String generate();
}
