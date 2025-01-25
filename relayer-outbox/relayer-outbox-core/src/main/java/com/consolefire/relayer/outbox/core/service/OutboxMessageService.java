package com.consolefire.relayer.outbox.core.service;

import com.consolefire.relayer.core.exception.RelayErrorCode;
import com.consolefire.relayer.core.handler.MessageHandlerResult;
import com.consolefire.relayer.outbox.model.OutboundMessage;
import java.io.Serializable;

public interface OutboxMessageService<ID extends Serializable> {

    boolean deleteOutboundMessage(String sourceIdentifier, OutboundMessage<ID> outboundMessage);

    boolean isGroupInSideline(String sourceIdentifier, String groupId);

    void markSidelined(String sourceIdentifier, OutboundMessage<ID> outboundMessage);

    void markCompleted(String sourceIdentifier, OutboundMessage<ID> outboundMessage);

    void markFailed(String sourceIdentifier, OutboundMessage<ID> outboundMessage,
        RelayErrorCode relayErrorCode, MessageHandlerResult messageHandlerResult);
}
