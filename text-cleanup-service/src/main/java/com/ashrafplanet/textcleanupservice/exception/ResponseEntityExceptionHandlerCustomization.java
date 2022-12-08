package com.ashrafplanet.textcleanupservice.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseEntityExceptionHandlerCustomization extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionDetails> handleAllExceptions(Exception ex, WebRequest request) {
		ExceptionDetails exceptionDetails = new ExceptionDetails(new Date(), HttpStatus.INTERNAL_SERVER_ERROR,
				messageSource.getMessage("message.exceptionDetails.handleAllExceptions", null, "error occurred",
						LocaleContextHolder.getLocale()),
				ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ExceptionDetails>(exceptionDetails, new HttpHeaders(), exceptionDetails.getStatus());
	}

	@ExceptionHandler(TextNotFoundException.class)
	public ResponseEntity<ExceptionDetails> handleTodoNotFoundException(TextNotFoundException ex, WebRequest request) {
		ExceptionDetails exceptionDetails = new ExceptionDetails(new Date(), HttpStatus.NOT_FOUND,
				messageSource.getMessage("message.exceptionDetails.handleTextNotFoundException", null,
						"no text found for", request.getLocale()) + " " + ex.getLocalizedMessage(),
				ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ExceptionDetails>(exceptionDetails, new HttpHeaders(), exceptionDetails.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// return string of first field error
		// ex.getFieldError().getDefaultMessage()
		// ex.getFieldErrors()
		List<String> validationErrors = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(Collectors.toList());
		ex.getBindingResult().getFieldErrors().stream()
				.map(error -> validationErrors.add(error.getObjectName() + ": " + error.getDefaultMessage()));
		ExceptionDetails exceptionDetails = new ExceptionDetails(new Date(), HttpStatus.BAD_REQUEST, validationErrors,
				ex.getMessage(), request.getDescription(false));
		return handleExceptionInternal(ex, exceptionDetails, headers, exceptionDetails.getStatus(), request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ExceptionDetails exceptionDetails = new ExceptionDetails(new Date(), HttpStatus.BAD_REQUEST,
				ex.getParameterName() + " "
						+ messageSource.getMessage("message.exceptionDetails.handleMissingServletRequestParameter",
								null, "parameter is missing", request.getLocale()),
				ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<Object>(exceptionDetails, new HttpHeaders(), exceptionDetails.getStatus());
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<ExceptionDetails> handleConstraintViolation(ConstraintViolationException ex,
			WebRequest request) {
		List<String> errors = new ArrayList<String>();
		for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
			errors.add(constraintViolation.getRootBeanClass().getName() + " " + constraintViolation.getPropertyPath()
					+ ": " + constraintViolation.getMessage());
		}
		ExceptionDetails exceptionDetails = new ExceptionDetails(new Date(), HttpStatus.BAD_REQUEST, errors,
				ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ExceptionDetails>(exceptionDetails, new HttpHeaders(), exceptionDetails.getStatus());
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<ExceptionDetails> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		ExceptionDetails exceptionDetails = new ExceptionDetails(new Date(), HttpStatus.BAD_REQUEST,
				ex.getName() + " "
						+ messageSource.getMessage("message.exceptionDetails.handleMethodArgumentTypeMismatch", null,
								"should be of type", request.getLocale())
						+ " " + ex.getRequiredType().getName(),
				ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ExceptionDetails>(exceptionDetails, new HttpHeaders(), exceptionDetails.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ExceptionDetails exceptionDetails = new ExceptionDetails(new Date(), HttpStatus.NOT_FOUND,
				messageSource.getMessage("message.exceptionDetails.handleNoHandlerFoundException", null,
						"no handler found for", request.getLocale()) + " " + ex.getHttpMethod() + " "
						+ ex.getRequestURL(),
				ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<Object>(exceptionDetails, new HttpHeaders(), exceptionDetails.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(ex.getMethod());
		stringBuilder
				.append(" "
						+ messageSource.getMessage("message.exceptionDetails.handleHttpRequestMethodNotSupported", null,
								"method is not supported for this request. supported methods are", request.getLocale())
						+ " ");
		ex.getSupportedHttpMethods().forEach(t -> stringBuilder.append(t + " "));
		ExceptionDetails exceptionDetails = new ExceptionDetails(new Date(), HttpStatus.METHOD_NOT_ALLOWED,
				stringBuilder.substring(0, stringBuilder.length() - 1), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<Object>(exceptionDetails, new HttpHeaders(), exceptionDetails.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(ex.getContentType());
		stringBuilder.append(" " + messageSource.getMessage("message.exceptionDetails.handleHttpMediaTypeNotSupported",
				null, "media type is not supported. supported media types are", request.getLocale()) + " ");
		ex.getSupportedMediaTypes().forEach(t -> stringBuilder.append(t + ", "));
		ExceptionDetails exceptionDetails = new ExceptionDetails(new Date(), HttpStatus.UNSUPPORTED_MEDIA_TYPE,
				stringBuilder.substring(0, stringBuilder.length() - 1), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<Object>(exceptionDetails, new HttpHeaders(), exceptionDetails.getStatus());
	}
}
