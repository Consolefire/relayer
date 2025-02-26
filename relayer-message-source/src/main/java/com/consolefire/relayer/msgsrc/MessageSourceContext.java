package com.consolefire.relayer.msgsrc;

public interface MessageSourceContext {

    MessageSourceRegistry getMessageSourceRegistry();

    void setMessageSourceRegistry(MessageSourceRegistry messageSourceRegistry);

}
