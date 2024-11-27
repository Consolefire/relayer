package com.consolefire.relayer.model.helper;

import com.consolefire.relayer.model.OutboundMessage;

import java.io.Serializable;

public class OutboundMessageCopier<ID extends Serializable> extends BaseMessageCopier<ID, OutboundMessage<ID>> {


    @Override
    protected void copyExtendedProperties(OutboundMessage<ID> sourceMessage, OutboundMessage<ID> copiedMessage) {
        copiedMessage.setState(sourceMessage.getState());
        copiedMessage.setRelayedAt(sourceMessage.getRelayedAt());
    }

    @Override
    protected OutboundMessage<ID> initTargetMessage() {
        return new OutboundMessage<>();
    }
}
