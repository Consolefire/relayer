package com.consolefire.relayer.core.msgsrc;

import java.util.function.Function;

@FunctionalInterface
public interface MessageSourceAwareExecutor<T, R> {

    R executeForMessageSource(String sourceIdentifier, Function<T, R> operation);

}
