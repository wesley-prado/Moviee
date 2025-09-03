package com.codemages.Moviee.cinema.session.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class SessionDatetimeOverlapException extends DataIntegrityViolationException {
  public SessionDatetimeOverlapException(String message) {
    super( message );
  }
}
