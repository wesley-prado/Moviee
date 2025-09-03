package com.codemages.Moviee.cinema.movie.exception;

import jakarta.persistence.EntityNotFoundException;

public class MovieNotFoundException extends EntityNotFoundException {
  public MovieNotFoundException(String message) {
    super( message );
  }
}
