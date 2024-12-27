package com.consolefire.relayer.core.exception;

public class RecoverableErrorException extends RelayErrorException {

    public RecoverableErrorException() {
    }

    public RecoverableErrorException(String message) {
        super(message);
    }

    public RecoverableErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecoverableErrorException(Throwable cause) {
        super(cause);
    }

    public RecoverableErrorException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
