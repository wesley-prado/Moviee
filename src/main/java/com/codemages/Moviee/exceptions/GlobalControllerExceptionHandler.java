package com.codemages.Moviee.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

  @ExceptionHandler(value = NoResourceFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorResponse> handleNotFound(
    NoResourceFoundException ex
  ) {
    return ErrorResponse.create( ex, HttpStatus.NOT_FOUND, null );
  }

  @ResponseStatus(value = HttpStatus.FORBIDDEN)
  @ExceptionHandler({
    AuthorizationDeniedException.class,
    AccessDeniedException.class,
    ForbiddenOperationException.class
  })
  public ResponseEntity<ErrorResponse> handleForbidden(
    Throwable ex
  ) {
    return ErrorResponse.create( ex, HttpStatus.FORBIDDEN, null );
  }
}
