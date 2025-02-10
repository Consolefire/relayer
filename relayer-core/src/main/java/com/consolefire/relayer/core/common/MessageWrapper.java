package com.consolefire.relayer.core.common;

import com.consolefire.relayer.model.Message;
import java.io.Serializable;
import java.util.UUID;
import lombok.NonNull;
import lombok.With;

public record MessageWrapper<ID extends Serializable, M extends Message<ID>>(
    @NonNull UUID readerIdentifier,
    @NonNull String messageSourceIdentifier,
    @With @NonNull M message)
    implements Comparable<MessageWrapper<ID, M>>, Serializable {

    public ID getMessageId() {
        return message.getMessageId();
    }

    @Override
    public int compareTo(@NonNull MessageWrapper<ID, M> o) {
        return this.message.compareTo(o.message);
    }

}
