package com.consolefire.relayer.outbox.model;

import com.consolefire.relayer.model.MessageState;
import java.io.Serializable;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@FieldNameConstants(asEnum = true)
public class SidelinedMessage<ID extends Serializable> extends OutboundMessage<ID> implements Serializable {

    public SidelinedMessage(ID messageId, Long messageSequence, String groupId, String payload, String headers,
        String metadata,
        MessageState state, Instant createdAt, Instant updatedAt, String channelName, Instant relayedAt, int relayCount,
        String relayError) {
        super(messageId, messageSequence, groupId, payload, headers, metadata, state, createdAt, updatedAt, channelName,
            relayedAt,
            relayCount, relayError);
    }

    public OutboundMessage<ID> toOutboundMessage() {
        return this;
    }
}


