package com.consolefire.relayer.core.data;

public class NonUniqueResultExistsException extends RuntimeException {

    public NonUniqueResultExistsException() {
    }

    public NonUniqueResultExistsException(String message) {
        super(message);
    }

    public NonUniqueResultExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonUniqueResultExistsException(Throwable cause) {
        super(cause);
    }

    public NonUniqueResultExistsException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
