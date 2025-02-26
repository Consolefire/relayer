package com.consolefire.relayer.msgsrc.data.handler;

import com.consolefire.relayer.msgsrc.data.event.MessageSourceEvent;

public interface MessageSourceEventHandler<E extends MessageSourceEvent> {

    void onMessageSourceEvent(E event);

}
