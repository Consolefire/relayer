package com.consolefire.relayer.core.exception;

public class RelayErrorException extends Exception {

    public RelayErrorException() {
    }

    public RelayErrorException(String message) {
        super(message);
    }

    public RelayErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public RelayErrorException(Throwable cause) {
        super(cause);
    }

    public RelayErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
