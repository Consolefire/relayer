package com.consolefire.relayer.outbox.core.processor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.consolefire.relayer.core.checkpoint.service.ReaderCheckpointService;
import com.consolefire.relayer.core.common.MessageSourceResolver;
import com.consolefire.relayer.core.common.ProcessableMessage;
import com.consolefire.relayer.core.exception.RecoverableErrorException;
import com.consolefire.relayer.core.exception.RelayErrorException;
import com.consolefire.relayer.core.exception.RelayErrorExceptionTranslator;
import com.consolefire.relayer.core.handler.MessageHandlerProvider;
import com.consolefire.relayer.core.handler.MessageHandlerResult;
import com.consolefire.relayer.core.handler.MessageHandlerResult.AlwaysSuccessResult;
import com.consolefire.relayer.core.processor.MessageProcessor;
import com.consolefire.relayer.outbox.core.data.repository.OutboundMessageReadRepository;
import com.consolefire.relayer.outbox.core.data.repository.OutboundMessageWriteRepository;
import com.consolefire.relayer.outbox.core.data.repository.SidelinedGroupReadRepository;
import com.consolefire.relayer.outbox.core.data.repository.SidelinedGroupWriteRepository;
import com.consolefire.relayer.outbox.core.data.repository.SidelinedMessageReadRepository;
import com.consolefire.relayer.outbox.core.data.repository.SidelinedMessageWriteRepository;
import com.consolefire.relayer.outbox.core.handler.OutboxMessageHandler;
import com.consolefire.relayer.outbox.core.service.OutboxMessageService;
import com.consolefire.relayer.outbox.model.OutboundMessage;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.Retry.Metrics;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class BaseOutboxMessageProcessorTest {

    private static final RetryConfig DEFAULT_RETRY_CONFIG;
    private static final RetryRegistry DEFAULT_RETRY_REGISTRY;
    private static final String SRC_ID_01 = "S01";

    static {
        DEFAULT_RETRY_CONFIG = RetryConfig.custom()
            .writableStackTraceEnabled(true)
            .maxAttempts(2)
            .waitDuration(Duration.ofMillis(1000))
            .retryOnException(e -> e instanceof RecoverableErrorException)
            .retryExceptions(IOException.class, TimeoutException.class, InterruptedException.class)
            .failAfterMaxAttempts(true)
            .build();
        DEFAULT_RETRY_REGISTRY = RetryRegistry.of(DEFAULT_RETRY_CONFIG);
    }


    protected MessageSourceResolver messageSourceResolver = mock();
    protected OutboundMessageReadRepository<UUID> outboundMessageReadRepository = mock();
    protected OutboundMessageWriteRepository<UUID> outboundMessageWriteRepository = mock();
    protected SidelinedGroupReadRepository sidelinedGroupReadRepository = mock();
    protected SidelinedGroupWriteRepository sidelinedGroupWriteRepository = mock();
    protected SidelinedMessageReadRepository<UUID> sidelinedMessageReadRepository = mock();
    protected SidelinedMessageWriteRepository<UUID> sidelinedMessageWriteRepository = mock();
    protected ReaderCheckpointService readerCheckpointService = mock();
    protected ExecutorService executorService = Executors.newSingleThreadExecutor();

    protected OutboxMessageHandler<UUID> outboxMessageHandler = mock();
    protected MessageHandlerProvider<UUID, OutboundMessage<UUID>> messageHandlerProvider = mock();
    protected RelayErrorExceptionTranslator relayErrorExceptionTranslator = mock();
    protected OutboxMessageService<UUID> outboxMessageService = mock();

    private final RetryRegistry retryRegistry = DEFAULT_RETRY_REGISTRY;
    private MessageProcessor<UUID, OutboundMessage<UUID>> baseOutboxMessageProcessor;

    @BeforeAll
    public void init() {
        OutboxMessageHandler outboxMessageHandler1 = new OutboxMessageHandler<UUID>() {
            @Override
            public MessageHandlerResult onMessage(OutboundMessage<UUID> message) throws RelayErrorException {
                Retry retry = RetryRegistry.of(DEFAULT_RETRY_CONFIG)
                    .retry(message.getGroupId(), Map.of("MESSAGE_ID", message.getMessageId().toString()));
                Metrics retryMetrics = retry.getMetrics();
                try {
                    return retry.executeCallable(() -> new AlwaysSuccessResult());
                } catch (Exception e) {
                    throw new RelayErrorException(e);
                }
            }
        };
        baseOutboxMessageProcessor = new BaseOutboxMessageProcessor<>(UUID.randomUUID(), outboxMessageService,
            messageHandlerProvider, relayErrorExceptionTranslator);
    }

    @BeforeEach
    void configureDefaultMocks() {
        when(messageHandlerProvider.getMessageHandler(any())).thenReturn(outboxMessageHandler);
    }

    @Test
    void testWhenMessageHandledSuccessfully() {
        OutboundMessage<UUID> message = OutboundMessage.<UUID>builder()
            .messageId(UUID.randomUUID())
            .groupId(UUID.randomUUID().toString())
            .attemptCount(3)
            .build();

        MessageHandlerResult expectedHandlerResult = new AlwaysSuccessResult();

        when(outboxMessageService.isGroupInSideline(SRC_ID_01, message.getGroupId())).thenReturn(false);

        try {
            when(outboxMessageHandler.onMessage(message)).thenReturn(expectedHandlerResult);
        } catch (RelayErrorException e) {
            throw new RuntimeException(e);
        }

        doNothing().when(outboxMessageService).markFailed(SRC_ID_01, message, null, expectedHandlerResult);

        try {
            baseOutboxMessageProcessor.process(new ProcessableMessage<>(message, 1, 1, SRC_ID_01, UUID.randomUUID()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testWhenMessageHandledUnsuccessfully() {
        OutboundMessage<UUID> message = OutboundMessage.<UUID>builder()
            .messageId(UUID.randomUUID())
            .groupId(UUID.randomUUID().toString())
            .attemptCount(3)
            .build();

        MessageHandlerResult expectedHandlerResult = new MessageHandlerResult() {
            @Override
            public boolean isSuccess() {
                return false;
            }

            @Override
            public String getErrorMessage() {
                return "Some error";
            }

            @Override
            public Instant getHandledAt() {
                return Instant.now();
            }

            @Override
            public Throwable getError() {
                return new RecoverableErrorException();
            }

        };

        when(outboxMessageService.isGroupInSideline(SRC_ID_01, message.getGroupId())).thenReturn(false);

        try {
            when(outboxMessageHandler.onMessage(message)).thenReturn(expectedHandlerResult);
        } catch (RelayErrorException e) {
            throw new RuntimeException(e);
        }

        doNothing().when(outboxMessageService).markCompleted(SRC_ID_01, message);

        try {
            baseOutboxMessageProcessor.process(new ProcessableMessage<>(message, 1, 1, SRC_ID_01, UUID.randomUUID()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testWhenMessageGroupInSideline() {
        OutboundMessage<UUID> message = OutboundMessage.<UUID>builder()
            .messageId(UUID.randomUUID())
            .groupId(UUID.randomUUID().toString())
            .attemptCount(3)
            .build();

        MessageHandlerResult expectedHandlerResult = new AlwaysSuccessResult();

        when(outboxMessageService.isGroupInSideline(SRC_ID_01, message.getGroupId())).thenReturn(true);

        try {
            when(outboxMessageHandler.onMessage(message)).thenReturn(expectedHandlerResult);
        } catch (RelayErrorException e) {
            throw new RuntimeException(e);
        }

        doNothing().when(outboxMessageService).markFailed(SRC_ID_01, message, null, expectedHandlerResult);

        try {
            baseOutboxMessageProcessor.process(new ProcessableMessage<>(message, 1, 1, SRC_ID_01, UUID.randomUUID()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
