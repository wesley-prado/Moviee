package com.codemages.Moviee.exceptions.client;

public class ClientNotFoundException extends RuntimeException {
  public ClientNotFoundException(String message) {
    super( message );
  }
}
