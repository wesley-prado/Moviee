package com.codemages.Moviee.cinema.ticket.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class SeatAlreadyBookedException extends DataIntegrityViolationException {
  public SeatAlreadyBookedException(String message) {
    super( message );
  }
}
