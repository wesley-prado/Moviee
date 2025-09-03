package com.codemages.Moviee.cinema.room.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class DuplicateRoomException extends DataIntegrityViolationException {
  public DuplicateRoomException(String msg) {
    super( msg );
  }
}
