package com.codemages.Moviee.user.exception;

public class DuplicateUserException extends RuntimeException {
  public DuplicateUserException(String message) {
    super( message );
  }
}
