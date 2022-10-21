package com.example.ticketbooking.exception.handler;

import com.example.ticketbooking.exception.TicketBookingServerException;
import com.example.ticketbooking.exception.error.Error;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;

@Component
@ControllerAdvice
public class TicketBookingExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<Object> handleIllegalArgumentException(final Exception ex, final WebRequest request) throws Exception {
        return super.handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(TicketBookingServerException.class)
    public final ResponseEntity<Object> handleTicketBookingServerException(final TicketBookingServerException ex,
                                                                           final WebRequest request) throws Exception {
        return super.handleExceptionInternal(ex, ex.getError(), new HttpHeaders(), ex.getError().getStatus(), request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationException(final ConstraintViolationException ex,
                                                                           final WebRequest request) throws Exception {
        final var errorResponses = new ArrayList<Error>();
        for (ConstraintViolation<?> violation :  ex.getConstraintViolations()) {
            errorResponses.add(new Error(String.format("Property [%s] %s", violation.getPropertyPath(), violation.getMessage()))
                    .withStatus(HttpStatus.BAD_REQUEST));
        }
        return super.handleExceptionInternal(ex, errorResponses, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
