package com.consolefire.relayer.msgsrc.data.event;

public interface MessageSourceEventNotifier {

    default <E extends MessageSourceEvent> void send(E event) {

    }

}
