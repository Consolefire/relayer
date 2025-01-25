package com.consolefire.relayer.core.handler;

import java.time.Instant;

public interface MessageHandlerResult {

    boolean isSuccess();

    String getErrorMessage();

    Instant getHandledAt();

    Throwable getError();

    public static class AlwaysSuccessResult implements MessageHandlerResult {

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public String getErrorMessage() {
            return "";
        }

        @Override
        public Instant getHandledAt() {
            return Instant.now();
        }

        @Override
        public Throwable getError() {
            return null;
        }

    }
}
