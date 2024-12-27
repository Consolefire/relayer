package com.consolefire.relayer.core.utils;

import com.consolefire.relayer.core.exception.RelayErrorException;
import com.consolefire.relayer.core.utils.MessageHandlerResult.AlwaysSuccessResult;
import com.consolefire.relayer.model.Message;
import java.io.Serializable;
import lombok.extern.slf4j.Slf4j;

@FunctionalInterface
public interface MessageHandler<ID extends Serializable, M extends Message<ID>> {

    MessageHandlerResult onMessage(M message) throws RelayErrorException;

    @Slf4j
    class NoOperationMessageHandler<I extends Serializable, T extends Message<I>>
        implements MessageHandler<I, T> {

        @Override
        public MessageHandlerResult onMessage(T message) {
            log.warn("Doing nothing with message: {}", message);
            return new AlwaysSuccessResult();
        }
    }
}
