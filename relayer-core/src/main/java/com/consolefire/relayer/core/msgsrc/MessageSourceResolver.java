package com.consolefire.relayer.core.msgsrc;

public interface MessageSourceResolver {

    MessageSourceContext resolve(String identifier);
}
