package com.consolefire.relayer.msgsrc;

import com.consolefire.relayer.msgsrc.data.entity.MessageSource;
import com.consolefire.relayer.msgsrc.data.event.MessageSourceEvent;
import com.fasterxml.jackson.databind.JsonNode;

public interface MessageSourceRegistry {

    default <E extends MessageSourceEvent> void notify(E event) {

    }

    MessageSource register(String identifier, JsonNode configurations);

    boolean unregister(String identifier);

    boolean activate(String identifier);

    boolean deactivate(String identifier);

    MessageSource get(String identifier);

}
