package com.codemages.moviee.exceptions.user;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.codemages.moviee.config.MediaTypes;
import com.codemages.moviee.controllers.v1.UserController;
import com.codemages.moviee.exceptions.global.ErrorResponse;

@RestControllerAdvice(basePackageClasses = UserController.class)
public class UserExceptionHandler {
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorResponse> handleUserException(UserException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.contentType(MediaTypes.DEFAULT_MEDIA_TYPE)
				.body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
						LocalDateTime.now(), null));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException e) {
		List<String> errors = e.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.toList();
		var error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
				"Field validation error", LocalDateTime.now(), errors);

		error.add(Link.of("http://my-api-docs.com/errors/validations", "about"));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.contentType(MediaTypes.DEFAULT_MEDIA_TYPE)
				.body(error);
	}

	@ExceptionHandler(DuplicateUserException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateUserException(
			DuplicateUserException e) {
		var error = new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage(),
				LocalDateTime.now(), null);
		error
				.add(Link.of("http://my-api-docs.com/errors/duplicated-user", "about"));

		return ResponseEntity.status(HttpStatus.CONFLICT)
				.contentType(MediaTypes.DEFAULT_MEDIA_TYPE)
				.body(error);
	}
}
