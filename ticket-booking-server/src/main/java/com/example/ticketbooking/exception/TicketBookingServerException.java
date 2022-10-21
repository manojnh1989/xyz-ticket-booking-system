package com.example.ticketbooking.exception;

import com.example.ticketbooking.exception.error.Error;
import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSourceResolvable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicketBookingServerException extends RuntimeException implements MessageSourceResolvable  {

    @Getter
    private final Error error;
    private Object[] arguments;

    public TicketBookingServerException(final Error error) {
        super("Operation Failed");
        this.error = error;
    }

    public TicketBookingServerException(final Error error, final Throwable cause) {
        super(getMessage(cause), cause);
        this.error = error;
    }

    public TicketBookingServerException(final Error error, final String defaultMessage) {
        super(defaultMessage);
        this.error = error;
    }

    @Override
    public String[] getCodes() {
        return new String[]{this.error.getMessage()};
    }

    @Override
    public Object[] getArguments() {
        return this.cloneArguments(this.arguments);
    }

    @Override
    public String getDefaultMessage() {
        return StringUtils.defaultIfBlank(this.getMessage(), "Operation Failed");
    }

    private Object[] cloneArguments(final Object[] arguments) {
        if (ArrayUtils.isEmpty(arguments)) {
            return null;
        } else {
            List<Object> argumentList = new ArrayList(Arrays.asList(arguments));
            return argumentList.toArray(new Object[0]);
        }
    }

    private static String getMessage(final Throwable cause) {
        return cause == null ? "Operation Failed" : StringUtils.defaultIfBlank(cause.getMessage(), "Operation Failed");
    }
}
