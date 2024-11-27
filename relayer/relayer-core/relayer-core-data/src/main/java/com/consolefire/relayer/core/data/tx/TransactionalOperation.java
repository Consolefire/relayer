package com.consolefire.relayer.core.data.tx;

import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public class TransactionalOperation<I, O> {

    private final Function<I, O> operation;

    public O doInTransaction(I input) {
        return operation.apply(input);
    }
}
