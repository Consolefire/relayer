package com.consolefire.relayer.model;

import java.io.Serializable;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@FieldNameConstants(asEnum = true)
public abstract class Message<ID extends Serializable>
    implements Comparable<Message<ID>>, Serializable {


    protected ID messageId;
    protected Long messageSequence;
    protected String groupId;
    @ToString.Exclude
    protected String payload;
    @ToString.Exclude
    protected String headers;
    @ToString.Exclude
    protected String metadata;
    protected MessageState state;
    protected Instant attemptedAt;
    protected int attemptCount;
    protected Instant createdAt;
    protected Instant updatedAt;

    public Message(ID messageId, Long messageSequence, String groupId, String payload, String headers, String metadata,
        MessageState state, Instant attemptedAt, int attemptCount,
        Instant createdAt, Instant updatedAt) {
        this.messageId = messageId;
        this.messageSequence = messageSequence;
        this.groupId = groupId;
        this.payload = payload;
        this.headers = headers;
        this.metadata = metadata;
        this.state = state;
        this.attemptedAt = attemptedAt;
        this.attemptCount = attemptCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public int compareTo(Message<ID> other) {
        if (null == this.messageSequence) {
            return -1;
        }
        if (null == other || null == other.messageSequence) {
            return 1;
        }
        return this.messageSequence.compareTo(other.messageSequence);
    }


}
