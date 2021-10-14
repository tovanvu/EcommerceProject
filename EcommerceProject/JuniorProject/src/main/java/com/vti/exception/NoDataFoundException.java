package com.vti.exception;

public class NoDataFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoDataFoundException(String message) {
		super(message);
	}

	public NoDataFoundException(Exception e) {
		super(e);
	}
}
