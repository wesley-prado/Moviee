package com.codemages.Moviee.cinema.session.exception;

import jakarta.persistence.EntityNotFoundException;

public class SessionNotFoundException extends EntityNotFoundException {
  public SessionNotFoundException(String message) {
    super( message );
  }
}
