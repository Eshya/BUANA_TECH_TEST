package com.eshya.buana_technical_test.exception;

import java.util.Date;

public class ErrorResponse {
    private Date timestamp;
    private String error;
    private String message;

    // Constructor
    public ErrorResponse(Date timestamp, String error, String message) {
        this.timestamp = timestamp;
        this.error = error;
        this.message = message;
    }

    // Getter dan setter (sesuai kebutuhan)
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
