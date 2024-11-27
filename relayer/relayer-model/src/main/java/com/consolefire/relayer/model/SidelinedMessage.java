package com.consolefire.relayer.model;

import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@FieldNameConstants(asEnum = true)
public class SidelinedMessage<ID extends Serializable> extends Message<ID> implements Serializable {

    protected int retryCount;
    protected Date lastTriedAt;

    @Builder(builderClassName = "DefaultBuilder")
    public SidelinedMessage(ID messageId, Long messageSequence, String groupId, String payload, String headers, String metadata, Date createdAt, Date updatedAt, int retryCount, Date lastTriedAt) {
        super(messageId, messageSequence, groupId, payload, headers, metadata, createdAt, updatedAt);
        this.retryCount = retryCount;
        this.lastTriedAt = lastTriedAt;
    }


    public OutboundMessage<ID> toOutboundMessage() {
        return null;
    }
}


