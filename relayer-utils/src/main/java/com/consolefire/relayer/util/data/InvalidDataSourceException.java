package com.consolefire.relayer.util.data;

public class InvalidDataSourceException extends RuntimeException {

    public InvalidDataSourceException() {
    }

    public InvalidDataSourceException(String message) {
        super(message);
    }

    public InvalidDataSourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDataSourceException(Throwable cause) {
        super(cause);
    }

    public InvalidDataSourceException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
