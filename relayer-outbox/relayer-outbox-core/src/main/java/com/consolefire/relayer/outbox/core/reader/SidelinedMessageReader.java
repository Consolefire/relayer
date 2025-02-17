package com.consolefire.relayer.outbox.core.reader;

import com.consolefire.relayer.core.reader.ParkedMessageReader;
import com.consolefire.relayer.outbox.model.OutboundMessage;
import com.consolefire.relayer.outbox.model.SidelinedMessage;
import java.io.Serializable;

public interface SidelinedMessageReader<ID extends Serializable>
    extends ParkedMessageReader<ID, OutboundMessage<ID>, SidelinedMessage<ID>> {

}
