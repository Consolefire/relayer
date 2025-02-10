package com.consolefire.relayer.core.utils;

import static io.github.resilience4j.core.IntervalFunction.ofExponentialBackoff;

import com.consolefire.relayer.core.exception.RecoverableErrorException;
import com.consolefire.relayer.core.handler.MessageHandlerResult;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.Retry.Metrics;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class RetryTest {

    public static final Duration INITIAL_INTERVAL = Duration.ofMillis(500);
    public static final Duration MAX_INTERVAL = Duration.ofMillis(600);
    private static final RetryConfig DEFAULT_RETRY_CONFIG;
    private static final RetryRegistry DEFAULT_RETRY_REGISTRY;
    private static final String NAME = "NAME-01";

    static {
        DEFAULT_RETRY_CONFIG = RetryConfig.custom()
            .writableStackTraceEnabled(true)
            .maxAttempts(10)
            .retryOnException(e -> e instanceof RecoverableErrorException)
            .retryExceptions(IOException.class, TimeoutException.class, InterruptedException.class)
            .failAfterMaxAttempts(true)
            .intervalFunction(ofExponentialBackoff(INITIAL_INTERVAL, 2.5, MAX_INTERVAL))
            .build();
        DEFAULT_RETRY_REGISTRY = RetryRegistry.of(DEFAULT_RETRY_CONFIG);
    }

    MessageHandlerResult doSomething() throws RecoverableErrorException, IOException {
        long millis = System.currentTimeMillis();
        log.info("Doing at: {}", millis);
        if (millis % 3 != 0) {
            log.error("Failed test");
            throw new IOException();
        }
        return MyResult.builder()
            .success(true).time(Instant.now()).errorMessage("none")
            .throwable(new RecoverableErrorException()).build();
    }

    @Test
    void shouldRetryOnRecoverableErrorException() {
        Retry retry = DEFAULT_RETRY_REGISTRY.retry(NAME, Map.of("X", "100"));

        try {
            MessageHandlerResult r = retry.executeCallable(() -> doSomething());
            log.info("Result: {}", r);
        } catch (Exception e) {
            log.error("*** Failed after {} tries", retry.getMetrics().getNumberOfTotalCalls());
        }
        Metrics metrics = retry.getMetrics();
        log.info("Total calls: {}", metrics.getNumberOfTotalCalls());
        log.info("NumberOfFailedCallsWithoutRetryAttempt: {}", metrics.getNumberOfFailedCallsWithoutRetryAttempt());
        log.info("NumberOfFailedCallsWithRetryAttempt: {}", metrics.getNumberOfFailedCallsWithRetryAttempt());
        log.info("NumberOfSuccessfulCallsWithRetryAttempt: {}", metrics.getNumberOfSuccessfulCallsWithRetryAttempt());
        log.info("NumberOfSuccessfulCallsWithoutRetryAttempt: {}",
            metrics.getNumberOfSuccessfulCallsWithoutRetryAttempt());
        log.info("Tags:  {}", retry.getTags());
    }

    @Data
    @Builder
    static class MyResult implements MessageHandlerResult {

        private boolean success;
        private String errorMessage;
        private Throwable throwable;
        private int count;
        private Instant time;


        @Override
        public boolean isSuccess() {
            return success;
        }

        @Override
        public String getErrorMessage() {
            return errorMessage;
        }

        @Override
        public Instant getHandledAt() {
            return Instant.now();
        }

        @Override
        public Throwable getError() {
            return throwable;
        }

    }

}
