package com.consolefire.relayer.outbox.core.service;

import com.consolefire.relayer.core.common.MessageSourceResolver;
import com.consolefire.relayer.core.exception.RelayErrorCode;
import com.consolefire.relayer.core.handler.MessageHandlerResult;
import com.consolefire.relayer.outbox.core.data.repository.OutboundMessageReadRepository;
import com.consolefire.relayer.outbox.core.data.repository.OutboundMessageWriteRepository;
import com.consolefire.relayer.outbox.core.data.repository.SidelinedGroupReadRepository;
import com.consolefire.relayer.outbox.core.data.repository.SidelinedGroupWriteRepository;
import com.consolefire.relayer.outbox.core.data.repository.SidelinedMessageReadRepository;
import com.consolefire.relayer.outbox.core.data.repository.SidelinedMessageWriteRepository;
import com.consolefire.relayer.outbox.model.OutboundMessage;
import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AbstractOutboxMessageService<ID extends Serializable>
    implements OutboxMessageService<ID> {

    protected final MessageSourceResolver messageSourceResolver;
    protected final OutboundMessageReadRepository<ID> outboundMessageReadRepository;
    protected final OutboundMessageWriteRepository<ID> outboundMessageWriteRepository;
    protected final SidelinedGroupReadRepository sidelinedGroupReadRepository;
    protected final SidelinedGroupWriteRepository sidelinedGroupWriteRepository;
    protected final SidelinedMessageReadRepository<ID> sidelinedMessageReadRepository;
    protected final SidelinedMessageWriteRepository<ID> sidelinedMessageWriteRepository;

    @Override
    public boolean deleteOutboundMessage(String sourceIdentifier, OutboundMessage<ID> outboundMessage) {
        return true;
    }

    @Override
    public boolean isGroupInSideline(String sourceIdentifier, String groupId) {
        return false;
    }

    @Override
    public void markSidelined(String sourceIdentifier, OutboundMessage<ID> outboundMessage) {

    }

    @Override
    public void markCompleted(String sourceIdentifier, OutboundMessage<ID> outboundMessage) {

    }

    @Override
    public void markFailed(String sourceIdentifier, OutboundMessage<ID> outboundMessage, RelayErrorCode relayErrorCode,
        MessageHandlerResult messageHandlerResult) {

    }
}
