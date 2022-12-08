package com.ashrafplanet.quicktodolistservice.exception;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ExceptionDetails {

	private Date timestamp;
	private HttpStatus status;
	private List<String> errors;
	private String message;
	private String path;

	public ExceptionDetails() {
		super();
	}

	public ExceptionDetails(Date timestamp, HttpStatus status, List<String> errors, String message, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.errors = errors;
		this.message = message;
		this.path = path;
	}

	public ExceptionDetails(Date timestamp, HttpStatus status, String errors, String message, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.errors = Arrays.asList(errors);
		this.message = message;
		this.path = path;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
