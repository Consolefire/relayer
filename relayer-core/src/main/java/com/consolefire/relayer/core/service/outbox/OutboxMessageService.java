package com.consolefire.relayer.core.service.outbox;

import com.consolefire.relayer.core.exception.RelayErrorCode;
import com.consolefire.relayer.core.utils.MessageHandlerResult;
import com.consolefire.relayer.model.outbox.OutboundMessage;
import java.io.Serializable;

public interface OutboxMessageService<ID extends Serializable> {

    boolean deleteOutboundMessage(String sourceIdentifier, OutboundMessage<ID> outboundMessage);

    boolean isGroupInSideline(String sourceIdentifier, String groupId);

    void markSidelined(String sourceIdentifier, OutboundMessage<ID> outboundMessage);

    void markCompleted(String sourceIdentifier, OutboundMessage<ID> outboundMessage);

    void markFailed(String sourceIdentifier, OutboundMessage<ID> outboundMessage,
        RelayErrorCode relayErrorCode, MessageHandlerResult messageHandlerResult);
}
