package com.codemages.moviee.exceptions;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.codemages.moviee.controllers.v1.UserController;

@RestControllerAdvice(basePackageClasses = UserController.class)
public class UserExceptionHandler {
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorResponse> handleUserException(UserException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
						LocalDateTime.now(), null));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException e) {
		List<String> errors = e.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.toList();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
						"Field validation error", LocalDateTime.now(), errors));
	}

	@ExceptionHandler(DuplicateUserException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateUserException(
			DuplicateUserException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage(),
						LocalDateTime.now(), null));
	}
}
