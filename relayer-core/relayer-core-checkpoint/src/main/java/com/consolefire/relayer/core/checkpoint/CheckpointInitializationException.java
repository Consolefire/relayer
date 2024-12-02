package com.consolefire.relayer.core.checkpoint;

public class CheckpointInitializationException extends RuntimeException {

    public CheckpointInitializationException() {
    }

    public CheckpointInitializationException(String message) {
        super(message);
    }

    public CheckpointInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckpointInitializationException(Throwable cause) {
        super(cause);
    }

    public CheckpointInitializationException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
