package com.consolefire.relayer.outbox.model;


import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.model.MessageState;
import java.io.Serializable;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@ToString
@NoArgsConstructor
@FieldNameConstants(asEnum = true)
public class OutboundMessage<ID extends Serializable> extends Message<ID> implements Serializable {

    protected String channelName;
    protected Instant relayedAt;
    protected int relayCount;
    protected String relayError;

    @Builder(builderClassName = "DefaultBuilder")
    public OutboundMessage(ID messageId, String groupId, String payload, String headers, String metadata,
        MessageState state, Instant createdAt, Instant updatedAt, String channelName, Instant relayedAt,
        int relayCount, String relayError) {
        super(messageId, groupId, payload, headers, metadata, state, createdAt, updatedAt);
        this.channelName = channelName;
        this.relayedAt = relayedAt;
        this.relayCount = relayCount;
        this.relayError = relayError;
    }
}
