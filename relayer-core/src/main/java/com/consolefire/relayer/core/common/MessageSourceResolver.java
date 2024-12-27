package com.consolefire.relayer.core.common;

public interface MessageSourceResolver {

    MessageSourceContext resolve(String identifier);
}
