package com.consolefire.relayer.core.reader;

import com.consolefire.relayer.model.outbox.SidelinedMessage;

import java.io.Serializable;

public interface SidelinedMessageSourceReader<ID extends Serializable>
        extends MessageSourceReader<ID, SidelinedMessage<ID>> {
}
