package com.consolefire.relayer.core.exception;

public interface RelayErrorExceptionTranslator {

    RelayErrorCode toErrorCode(Throwable exception);

}
