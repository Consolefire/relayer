package com.consolefire.relayer.core.checkpoint.events;

import com.consolefire.relayer.core.common.MessageProcessingState;
import lombok.NonNull;

import java.io.Serializable;
import java.util.UUID;

public record MessageStateEvent<ID extends Serializable>(
        @NonNull UUID readerId, @NonNull ID messageId, @NonNull MessageProcessingState processingState) {

    @Override
    public String toString() {
        return new StringBuilder()
                .append("MessageStateEvent{")
                .append("messageId=").append(messageId)
                .append(", processingState=").append(processingState)
                .append(", readerId=").append(readerId)
                .append('}').toString();
    }
}
