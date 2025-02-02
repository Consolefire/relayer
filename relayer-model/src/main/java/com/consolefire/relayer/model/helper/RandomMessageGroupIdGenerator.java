package com.consolefire.relayer.model.helper;

import java.util.UUID;

public class RandomMessageGroupIdGenerator<P, M> implements MessageGroupIdGenerator<P, M> {

    @Override
    public String generate(P o, M metadata) {
        return UUID.randomUUID().toString();
    }
}
