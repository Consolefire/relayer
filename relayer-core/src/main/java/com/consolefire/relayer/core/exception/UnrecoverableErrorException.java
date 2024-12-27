package com.consolefire.relayer.core.exception;

public class UnrecoverableErrorException extends RelayErrorException {

    public UnrecoverableErrorException() {
    }

    public UnrecoverableErrorException(String message) {
        super(message);
    }

    public UnrecoverableErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnrecoverableErrorException(Throwable cause) {
        super(cause);
    }

    public UnrecoverableErrorException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
