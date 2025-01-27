package com.consolefire.relayer.writer;

public class MessageWriterErrorException extends RuntimeException {

    public MessageWriterErrorException() {
    }

    public MessageWriterErrorException(String message) {
        super(message);
    }

    public MessageWriterErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageWriterErrorException(Throwable cause) {
        super(cause);
    }

    public MessageWriterErrorException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
