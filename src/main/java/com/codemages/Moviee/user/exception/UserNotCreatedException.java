package com.codemages.Moviee.user.exception;

import java.io.Serial;

public class UserNotCreatedException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 1L;

  public UserNotCreatedException(String message) {
    super( message );
  }

  public UserNotCreatedException(String message, Throwable cause) {
    super( message, cause );
  }
}
