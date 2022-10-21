package com.example.ticketbooking.exception.error;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class Error implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    private HttpStatus status;

    public Error() {}

    public Error(final String message) {
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public Error withStatus(final HttpStatus status) {
        this.status = status;
        return this;
    }

    public final HttpStatus getStatus() {
        return this.status;
    }
}
