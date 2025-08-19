package com.codemages.Moviee.exceptions;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class OperationFailureHandler {
  @ExceptionHandler(ForbiddenOperationException.class)
  public ResponseEntity<ErrorResponse> handleForbiddenOperation(
    ForbiddenOperationException ex
  ) {
    var error = new ErrorResponse(
      403, ex.getMessage(),
      LocalDateTime.now(), null
    );
    error.add( Link.of( "https://my-api-docs.com/errors/forbidden", "about" ) );

    return ResponseEntity.status( HttpStatus.FORBIDDEN )
      .contentType( MediaTypes.HAL_JSON )
      .body( error );
  }
}
