package com.consolefire.relayer.outbox.core.handler;

import com.consolefire.relayer.core.handler.MessageHandler;
import com.consolefire.relayer.outbox.model.OutboundMessage;
import java.io.Serializable;

public interface OutboxMessageHandler<ID extends Serializable> extends MessageHandler<ID, OutboundMessage<ID>> {

}
