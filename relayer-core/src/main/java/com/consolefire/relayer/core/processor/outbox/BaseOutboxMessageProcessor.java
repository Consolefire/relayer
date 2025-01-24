package com.consolefire.relayer.core.processor.outbox;

import com.consolefire.relayer.core.common.ProcessableMessage;
import com.consolefire.relayer.core.exception.RelayErrorCode;
import com.consolefire.relayer.core.exception.RelayErrorExceptionTranslator;
import com.consolefire.relayer.core.processor.MessageProcessor;
import com.consolefire.relayer.core.service.outbox.OutboxMessageService;
import com.consolefire.relayer.core.utils.MessageHandlerProvider;
import com.consolefire.relayer.core.utils.MessageHandlerResult;
import com.consolefire.relayer.model.outbox.OutboundMessage;
import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseOutboxMessageProcessor<ID extends Serializable> //
    implements MessageProcessor<ID, OutboundMessage<ID>> {

    @Getter
    private final UUID identifier;
    private final OutboxMessageService<ID> outboxMessageService;
    private final MessageHandlerProvider<ID, OutboundMessage<ID>> outboxMessageHandler;
    private final RelayErrorExceptionTranslator relayErrorExceptionTranslator;

    public BaseOutboxMessageProcessor(
        @NonNull UUID identifier,
        @NonNull OutboxMessageService<ID> outboxMessageService,
        @NonNull MessageHandlerProvider<ID, OutboundMessage<ID>> outboxMessageHandler,
        @NonNull RelayErrorExceptionTranslator relayErrorExceptionTranslator) {
        this.identifier = identifier;
        this.outboxMessageService = outboxMessageService;
        this.outboxMessageHandler = outboxMessageHandler;
        this.relayErrorExceptionTranslator = relayErrorExceptionTranslator;
    }

    @Override
    public void process(ProcessableMessage<ID, OutboundMessage<ID>> processableMessage) throws Exception {
        log.info("Processing message: {}", processableMessage);
        final String sourceIdentifier = processableMessage.getSourceIdentifier();
        final OutboundMessage<ID> message = processableMessage.getMessage();
        try {
            if (null == message.getGroupId() || message.getGroupId().isBlank()) {
                // This is non-reachable. Keeping here for testing purpose.
                log.warn("Message with ID: {} does not have groupId", message.getMessageId());
            } else {
                boolean isSidelined = outboxMessageService.isGroupInSideline(sourceIdentifier, message.getGroupId());
                if (isSidelined) {
                    log.warn("Message with ID: {} and groupID: {} is in sidelined groups", message.getMessageId(),
                        message.getGroupId());
                    outboxMessageService.markSidelined(sourceIdentifier, message);
                    return;
                }
            }
            log.debug("Handle message ID: {} with retry", message.getMessageId());
            MessageHandlerResult result = outboxMessageHandler.getMessageHandler(message)
                .onMessage(message);
            if (result.isSuccess()) {
                log.debug("Message with id: {} handled successfully.", message.getMessageId());
                outboxMessageService.markCompleted(processableMessage.getSourceIdentifier(), message);
            } else {
                RelayErrorCode relayErrorCode = relayErrorExceptionTranslator.toErrorCode(result.getError());
                outboxMessageService.markFailed(sourceIdentifier, message, relayErrorCode, result);
            }
        } finally {
        }
    }

}
