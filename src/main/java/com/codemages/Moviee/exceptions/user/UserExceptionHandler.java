package com.codemages.Moviee.exceptions.user;

import com.codemages.Moviee.controllers.v1.UserController;
import com.codemages.Moviee.exceptions.global.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice(basePackageClasses = UserController.class)
public class UserExceptionHandler {
  @ExceptionHandler(UserException.class)
  public ResponseEntity<ErrorResponse> handleUserException(UserException e) {
	return ResponseEntity.status( HttpStatus.BAD_REQUEST )
			.contentType( MediaTypes.HAL_JSON )
			.body( new ErrorResponse(
					HttpStatus.BAD_REQUEST.value(), e.getMessage(),
					LocalDateTime.now(), null
			) );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
		  MethodArgumentNotValidException e
  ) {
	List<String> errors = e.getBindingResult().getFieldErrors().stream()
			.map( error -> error.getField() + ": " + error.getDefaultMessage() )
			.toList();
	var error = new ErrorResponse(
			HttpStatus.BAD_REQUEST.value(),
			"Field validation error", LocalDateTime.now(), errors
	);

	error.add( Link.of( "https://my-api-docs.com/errors/validations", "about" ) );

	return ResponseEntity.status( HttpStatus.BAD_REQUEST )
			.contentType( MediaTypes.HAL_JSON )
			.body( error );
  }

  @ExceptionHandler(DuplicateUserException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateUserException(
		  DuplicateUserException e
  ) {
	var error = new ErrorResponse(
			HttpStatus.CONFLICT.value(), e.getMessage(),
			LocalDateTime.now(), null
	);
	error
			.add( Link.of( "https://my-api-docs.com/errors/duplicated-user", "about" ) );

	return ResponseEntity.status( HttpStatus.CONFLICT )
			.contentType( MediaTypes.HAL_JSON )
			.body( error );
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFoundException(
		  UserNotFoundException e
  ) {
	var error = new ErrorResponse(
			HttpStatus.NOT_FOUND.value(), e.getMessage(),
			LocalDateTime.now(), null
	);
	error.add( Link.of( "https://my-api-docs.com/errors/user-not-found", "about" ) );

	return ResponseEntity.status( HttpStatus.NOT_FOUND )
			.contentType( MediaTypes.HAL_JSON )
			.body( error );
  }

  @ExceptionHandler(UserNotCreatedException.class)
  public ResponseEntity<ErrorResponse> handleUserNotCreatedException(
		  UserNotCreatedException e
  ) {
	var error = new ErrorResponse(
			HttpStatus.BAD_REQUEST.value(), e.getMessage(),
			LocalDateTime.now(), null
	);
	error.add( Link.of( "https://my-api-docs.com/errors/user-not-created", "about" ) );

	return ResponseEntity.status( HttpStatus.BAD_REQUEST )
			.contentType( MediaTypes.HAL_JSON )
			.body( error );
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
		  DataIntegrityViolationException e
  ) {
	var error = new ErrorResponse(
			HttpStatus.BAD_REQUEST.value(), "Data integrity violation: " + e.getMessage(),
			LocalDateTime.now(), null
	);
	error.add( Link.of( "https://my-api-docs.com/errors/data-integrity-violation", "about" ) );
	return ResponseEntity.status( HttpStatus.BAD_REQUEST )
			.contentType( MediaTypes.HAL_JSON )
			.body( error );
  }
}
