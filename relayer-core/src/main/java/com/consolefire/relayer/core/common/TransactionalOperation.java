package com.consolefire.relayer.core.common;

import java.util.function.Function;

public interface TransactionalOperation<T, R> extends Function<T, R> {


}
