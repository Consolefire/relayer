package com.consolefire.relayer.core.reader;

import com.consolefire.relayer.model.OutboundMessage;

import java.io.Serializable;

public interface OutboundMessageSourceReader<ID extends Serializable>
        extends MessageSourceReader<ID, OutboundMessage<ID>> {
}
