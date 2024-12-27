package com.consolefire.relayer.core.processor;

import com.consolefire.relayer.core.common.ProcessableMessage;
import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.util.ConsumerQueue;
import java.io.Serializable;

public class MessageProcessorQueue<ID extends Serializable, M extends Message<ID>> //
    extends ConsumerQueue<ProcessableMessage<ID, M>> {


}
