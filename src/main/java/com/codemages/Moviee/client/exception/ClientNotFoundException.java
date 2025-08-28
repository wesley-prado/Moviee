package com.codemages.Moviee.client.exception;

public class ClientNotFoundException extends RuntimeException {
  public ClientNotFoundException(String message) {
    super( message );
  }
}
