package com.consolefire.relayer.msgsrc.data.event;

import com.consolefire.relayer.msgsrc.data.entity.MessageSource.State;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageSourceRegisteredEvent extends MessageSourceEvent {

    protected final State state;
    protected final JsonNode configuration;

    @Builder
    public MessageSourceRegisteredEvent(String identifier, State state, JsonNode configuration) {
        super(identifier);
        this.state = state;
        this.configuration = configuration;
    }
}
