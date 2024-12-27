package com.consolefire.relayer.core.exception;

public enum RelayErrorCode {

    _4XX,
    _5XX;


    public static boolean isRecoverable(RelayErrorCode errorCode) {
        return null != errorCode && errorCode.equals(_5XX);
    }
}
