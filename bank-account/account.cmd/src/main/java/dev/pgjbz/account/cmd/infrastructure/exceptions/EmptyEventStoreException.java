package dev.pgjbz.account.cmd.infrastructure.exceptions;

public class EmptyEventStoreException extends RuntimeException{
    public EmptyEventStoreException() {
        super("empty event source store cannot be republished");
    }
}
