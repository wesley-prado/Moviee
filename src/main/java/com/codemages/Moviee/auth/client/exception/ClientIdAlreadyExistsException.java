package com.codemages.Moviee.auth.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // Retorna o status 409 Conflict
public class ClientIdAlreadyExistsException extends RuntimeException {

  public ClientIdAlreadyExistsException(String message) {
    super( message );
  }
}