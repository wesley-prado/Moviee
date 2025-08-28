package com.codemages.Moviee.auth.security.password;

public class PasswordGeneratorConstants {
  private PasswordGeneratorConstants() {
    // Prevent instantiation
  }

  public static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
  public static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  public static final String DIGITS = "0123456789";
  public static final String SPECIAL = "!@#$%^&*()_+-=[]{}|;:,.<>?";
  public static final int MIN_LENGTH = 8;
  public static final int MAX_LENGTH = 20;
}
