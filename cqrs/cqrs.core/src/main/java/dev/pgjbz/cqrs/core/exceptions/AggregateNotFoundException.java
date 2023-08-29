package dev.pgjbz.cqrs.core.exceptions;

public class AggregateNotFoundException extends RuntimeException {
    public AggregateNotFoundException(final String message) {
        super(message);
    }
}
