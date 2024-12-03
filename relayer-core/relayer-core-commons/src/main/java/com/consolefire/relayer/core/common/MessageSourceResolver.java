package com.consolefire.relayer.core.common;

public interface MessageSourceResolver {

    MessageSource resolve(String identifier);
}
