package com.consolefire.relayer.model;


import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@FieldNameConstants(asEnum = true)
public class OutboundMessage<ID extends Serializable> extends Message<ID> implements Serializable {

    protected String channelName;
    protected MessageState state;
    protected Date relayedAt;
    protected int relayCount;

    @Builder(builderClassName = "DefaultBuilder")
    public OutboundMessage(ID messageId, Long messageSequence, String groupId, String payload, String headers, String metadata, Date createdAt, Date updatedAt, String channelName, MessageState state, Date relayedAt, int relayCount) {
        super(messageId, messageSequence, groupId, payload, headers, metadata, createdAt, updatedAt);
        this.channelName = channelName;
        this.state = state;
        this.relayedAt = relayedAt;
        this.relayCount = relayCount;
    }
}
