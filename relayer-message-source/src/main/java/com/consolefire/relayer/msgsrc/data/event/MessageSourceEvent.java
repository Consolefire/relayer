package com.consolefire.relayer.msgsrc.data.event;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class MessageSourceEvent {

    protected final String identifier;

    public MessageSourceEvent(String identifier) {
        this.identifier = identifier;
    }
}
