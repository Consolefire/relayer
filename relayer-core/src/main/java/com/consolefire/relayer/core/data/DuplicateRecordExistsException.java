package com.consolefire.relayer.core.data;

public class DuplicateRecordExistsException extends RuntimeException {

    public DuplicateRecordExistsException() {
    }

    public DuplicateRecordExistsException(String message) {
        super(message);
    }

    public DuplicateRecordExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateRecordExistsException(Throwable cause) {
        super(cause);
    }

    public DuplicateRecordExistsException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
