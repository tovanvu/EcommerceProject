package com.vti.exception;

import lombok.Getter;
import org.springframework.validation.Errors;

@SuppressWarnings("serial")
public class ValidationErrorException extends RuntimeException {
	@Getter
	private Errors errors;

	public ValidationErrorException(Errors errors) {
		super();
		this.errors = errors;
	}
}
