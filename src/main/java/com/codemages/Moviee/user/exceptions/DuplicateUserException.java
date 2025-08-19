package com.codemages.Moviee.user.exceptions;

public class DuplicateUserException extends RuntimeException {
  public DuplicateUserException(String message) {
    super( message );
  }
}
