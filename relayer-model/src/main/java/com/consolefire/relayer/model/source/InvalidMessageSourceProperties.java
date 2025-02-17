package com.consolefire.relayer.model.source;

public class InvalidMessageSourceProperties extends RuntimeException {

    public InvalidMessageSourceProperties() {
    }

    public InvalidMessageSourceProperties(String message) {
        super(message);
    }

    public InvalidMessageSourceProperties(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMessageSourceProperties(Throwable cause) {
        super(cause);
    }

    public InvalidMessageSourceProperties(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
