package com.codemages.moviee.exceptions;

public class DuplicateUserException extends RuntimeException {
	public DuplicateUserException(String message) {
		super(message);
	}

	public DuplicateUserException(String message, Throwable cause) {
		super(message, cause);
	}

}
