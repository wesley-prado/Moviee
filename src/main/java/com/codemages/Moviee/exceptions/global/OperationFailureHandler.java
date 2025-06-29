package com.codemages.Moviee.exceptions.global;

import java.time.LocalDateTime;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.codemages.Moviee.config.MediaTypes;

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
    error.add( Link.of( "http://my-api-docs.com/errors/forbidden/about", "about" ) );

    return ResponseEntity.status( HttpStatus.FORBIDDEN )
      .contentType( MediaTypes.DEFAULT_MEDIA_TYPE )
      .body( error );
  }
}
