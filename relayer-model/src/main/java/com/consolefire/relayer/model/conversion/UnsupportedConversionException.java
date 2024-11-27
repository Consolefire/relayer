package com.consolefire.relayer.model.conversion;

public class UnsupportedConversionException extends RuntimeException {

    public UnsupportedConversionException() {
    }

    public UnsupportedConversionException(String message) {
        super(message);
    }

    public UnsupportedConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedConversionException(Throwable cause) {
        super(cause);
    }

    public UnsupportedConversionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
