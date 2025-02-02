package com.consolefire.relayer.model.validation;

import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.model.MessageState;
import java.time.Instant;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SimpleTestMessage extends Message<Long> {

    @Builder
    public SimpleTestMessage(Long messageId, Long messageSequence, String groupId, String payload, String headers,
        String metadata, MessageState state, Instant createdAt, Instant updatedAt) {
        super(messageId, messageSequence, groupId, payload, headers, metadata, state, createdAt, updatedAt);
    }
}
