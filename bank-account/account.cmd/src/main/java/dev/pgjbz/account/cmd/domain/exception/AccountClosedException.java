package dev.pgjbz.account.cmd.domain.exception;

public class AccountClosedException extends RuntimeException {
    public AccountClosedException(final String message) {
        super(message);
    }
}
