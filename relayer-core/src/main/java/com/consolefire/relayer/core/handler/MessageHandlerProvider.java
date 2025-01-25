package com.consolefire.relayer.core.handler;

import com.consolefire.relayer.model.Message;
import java.io.Serializable;

public interface MessageHandlerProvider<ID extends Serializable, M extends Message<ID>> {

    MessageHandler<ID, M> getMessageHandler(M message);

}
