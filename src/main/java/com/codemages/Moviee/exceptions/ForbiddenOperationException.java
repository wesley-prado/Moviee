package com.codemages.moviee.exceptions;

public class ForbiddenOperationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ForbiddenOperationException(String message) {
		super(message);
	}

	public ForbiddenOperationException(String message, Throwable cause) {
		super(message, cause);
	}

}
