package com.consolefire.relayer.core.utils.outbox;

import com.consolefire.relayer.core.utils.MessageHandler;
import com.consolefire.relayer.model.outbox.OutboundMessage;
import java.io.Serializable;

public interface OutboxMessageHandler<ID extends Serializable> extends MessageHandler<ID, OutboundMessage<ID>> {

}
