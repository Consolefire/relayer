package com.consolefire.relayer.core.checkpoint.events;

import java.io.Serializable;

@FunctionalInterface
public interface MessageStateEventListener<ID extends Serializable> {

    void on(MessageStateEvent<ID> event);

}
