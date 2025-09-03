package com.codemages.Moviee.cinema.room.exception;

import jakarta.persistence.EntityNotFoundException;

public class RoomNotFoundException extends EntityNotFoundException {
  public RoomNotFoundException(String message) {
    super( message );
  }
}
