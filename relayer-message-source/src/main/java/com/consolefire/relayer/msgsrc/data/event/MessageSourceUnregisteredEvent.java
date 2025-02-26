package com.consolefire.relayer.msgsrc.data.event;

public class MessageSourceUnregisteredEvent extends MessageSourceEvent {

    public MessageSourceUnregisteredEvent(String identifier) {
        super(identifier);
    }
}
