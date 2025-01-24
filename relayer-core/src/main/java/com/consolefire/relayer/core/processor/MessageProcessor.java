package com.consolefire.relayer.core.processor;

import com.consolefire.relayer.core.common.ProcessableMessage;
import com.consolefire.relayer.model.Message;
import java.io.Serializable;

public interface MessageProcessor<ID extends Serializable, M extends Message<ID>> {

    void process(ProcessableMessage<ID, M> processableMessage) throws Exception;

}
