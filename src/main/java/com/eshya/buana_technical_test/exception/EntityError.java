package com.eshya.buana_technical_test.exception;

import java.util.Date;

public class EntityError extends Throwable {

	private Date timestamp;

	private String message;

	private String details;
	
	private String error_code;


	public EntityError(Date timestamp, String error_code, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.error_code = error_code;
		this.message = message;
		this.details = details;
		
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
