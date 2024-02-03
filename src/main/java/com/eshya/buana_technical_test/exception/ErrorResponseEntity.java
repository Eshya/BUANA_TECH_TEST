package com.eshya.buana_technical_test.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ErrorResponseEntity {

	private HttpStatus error_code;
	
	private String field;
	
	private String value;
	
	private String message;
	
	private Date timestamp;

	public HttpStatus getError_Code() {
		return error_code;
	}

	public void setError_Code(HttpStatus error_code) {
		this.error_code = error_code;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	
	
	
	
}
