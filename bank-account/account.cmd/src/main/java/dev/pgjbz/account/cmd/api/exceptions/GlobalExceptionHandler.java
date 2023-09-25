package dev.pgjbz.account.cmd.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.pgjbz.account.cmd.domain.exception.AccountClosedException;
import dev.pgjbz.account.cmd.domain.exception.InvalidAmountException;
import dev.pgjbz.account.common.dto.BaseResponse;
import dev.pgjbz.cqrs.core.exceptions.AggregateNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String CLIENT_MADE_A_BAD_REQUEST = "Client made a bad request - {}";

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<BaseResponse> handle(final IllegalStateException ex) {
        final String message = ex.getMessage();
        log.warn(CLIENT_MADE_A_BAD_REQUEST, message);
        int status = HttpStatus.BAD_REQUEST.value();
        return ResponseEntity
                .status(status)
                .body(new StandardError(message, status));
    }

    @ExceptionHandler(AggregateNotFoundException.class)
    public ResponseEntity<BaseResponse> handle(final AggregateNotFoundException ex) {
        final String message = ex.getMessage();
        log.warn(CLIENT_MADE_A_BAD_REQUEST, message);
        int status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status).body(new StandardError(message, status));
    }

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<BaseResponse> handle(final InvalidAmountException ex) {
        int status = HttpStatus.BAD_REQUEST.value();
        log.warn(CLIENT_MADE_A_BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(status).body(new StandardError(ex.getMessage(), status));
    }

    @ExceptionHandler(AccountClosedException.class)
    public ResponseEntity<BaseResponse> handle(final AccountClosedException ex) {
        int status = HttpStatus.BAD_REQUEST.value();
        log.warn(CLIENT_MADE_A_BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(status).body(new StandardError(ex.getMessage(), status));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handle(final Exception ex) {
        final String safeErrorMessage = "Error while processing request";
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        log.error(safeErrorMessage, ex);
        return ResponseEntity.status(status).body(new StandardError(safeErrorMessage, status));
    }

}
