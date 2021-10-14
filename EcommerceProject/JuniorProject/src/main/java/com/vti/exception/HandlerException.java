package com.vti.exception;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.vti.model.login.response.ErrorResponse;

@RestControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {

	private final MessageSource messageSource;

	public HandlerException(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ErrorResponse resError = new ErrorResponse();
		if (ex instanceof HttpMessageNotReadableException) {
			HttpMessageNotReadableException err = (HttpMessageNotReadableException) ex;
			Throwable throwable = err.getCause();
			if (throwable instanceof InvalidFormatException) {
				/*
				 * HttpMessageNotReadableException have no properties for accessing things like
				 * the error code and field/object name. Thus Im using pattern to get fieldName
				 * errors. by LinhPV6956
				 */
				InvalidFormatException error = (InvalidFormatException) throwable;
				String errorMessage = error.getPathReference();

				Pattern pattern = Pattern.compile(".*\\[\"(.*)\"\\]");
				Matcher matcher = pattern.matcher(errorMessage);

				if (matcher.find() && matcher.group(1) != null) {
					String fieldName = matcher.group(1).toString();
					resError.addError(fieldName, messageSource.getMessage("Parameter.invalid.format.error",
							new String[] { fieldName }, LocaleContextHolder.getLocale()));

				} else {
					resError.setMessage(ex.getMessage());
				}
			} else {
				resError.setMessage(ex.getMessage());
			}
		} else {
			resError.setMessage(ex.getMessage());
		}
		return super.handleExceptionInternal(ex, resError, headers, status, request);
	}

	@ExceptionHandler
	public ResponseEntity<Object> handlerAllException(Exception e, WebRequest request) {
		ErrorResponse error = new ErrorResponse();
		error.setMessage(e.getMessage());
		return super.handleExceptionInternal(e, error, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler
	public ResponseEntity<Object> handlerValidationExceptionException(ValidationErrorException ex, WebRequest request) {
		ErrorResponse errors = new ErrorResponse();
		if (ex instanceof ValidationErrorException) {

			for (FieldError error : ex.getErrors().getFieldErrors()) {
				errors.addError(error.getField(), getMessage(error, request));
			}
		}
		return super.handleExceptionInternal(ex, errors, null, HttpStatus.BAD_REQUEST, request);
	}

	/**
	 * Handle MethodArgumentTypeMismatchException.
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleCommonException(MethodArgumentTypeMismatchException ex, WebRequest request) {
		ErrorResponse errors = new ErrorResponse();
		errors.setMessage(messageSource.getMessage("Parameter.invalid.format.error", new Object[] { ex.getName() },
				LocaleContextHolder.getLocale()));
		return super.handleExceptionInternal(ex, errors, null, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ AccessDeniedException.class, UsernameNotFoundException.class })
	public ResponseEntity<Object> handleForbidenException(AccessDeniedException ex, WebRequest request) {
		ErrorResponse errors = new ErrorResponse();
		errors.setField(String.format(HttpStatus.FORBIDDEN.name() + ": %s ", request.getRemoteUser()));
		errors.setMessage(ex.getMessage());
		return super.handleExceptionInternal(ex, errors, null, HttpStatus.FORBIDDEN, request);
	}

	private String getMessage(MessageSourceResolvable resolvable, WebRequest request) {
		return messageSource.getMessage(resolvable, request.getLocale());
	}
}