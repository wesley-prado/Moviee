package com.codemages.Moviee.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

  @ExceptionHandler(value = NoResourceFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorResponse> handleNotFound(
    NoResourceFoundException ex
  ) {
    return ErrorResponse.create( ex.getMessage(), HttpStatus.NOT_FOUND, null );
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
    return ErrorResponse.create( ex.getMessage(), HttpStatus.FORBIDDEN, null );
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleEntityNotFoundException(
    EntityNotFoundException ex
  ) {
    return ErrorResponse.create( ex.getMessage(), HttpStatus.BAD_REQUEST, null );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
    MethodArgumentNotValidException ex
  ) {
    List<String> errors = ex.getBindingResult().getFieldErrors().stream()
      .map( error -> error.getField() + ": " + error.getDefaultMessage() )
      .toList();
    return ErrorResponse.create(
      "Houve uma falha ao validar os campos da requisição",
      HttpStatus.BAD_REQUEST,
      errors
    );
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorResponse> handleGenericException(
    Exception ex
  ) {
    return ErrorResponse.create( ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null );
  }
}
