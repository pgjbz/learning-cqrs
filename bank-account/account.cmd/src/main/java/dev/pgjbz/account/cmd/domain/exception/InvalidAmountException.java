package dev.pgjbz.account.cmd.domain.exception;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(final String message) {
        super(message);
    }
}
