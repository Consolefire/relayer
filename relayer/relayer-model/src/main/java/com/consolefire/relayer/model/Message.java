package com.consolefire.relayer.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
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
    protected Date createdAt;
    protected Date updatedAt;

    public Message(
            ID messageId, Long messageSequence, String groupId, String payload, String headers,
            String metadata, Date createdAt, Date updatedAt) {
        this.messageId = messageId;
        this.messageSequence = messageSequence;
        this.groupId = groupId;
        this.payload = payload;
        this.headers = headers;
        this.metadata = metadata;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public int compareTo(Message<ID> other) {
        if (null == other || null == other.messageSequence) {
            return 1;
        }
        return this.messageSequence.compareTo(other.messageSequence);
    }


}
