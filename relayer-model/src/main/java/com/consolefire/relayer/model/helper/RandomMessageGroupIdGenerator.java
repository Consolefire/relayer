package com.consolefire.relayer.model.helper;

import java.util.UUID;

public class RandomMessageGroupIdGenerator implements MessageGroupIdGenerator<Object, Object> {
    @Override
    public String generate(Object o, Object metadata) {
        return UUID.randomUUID().toString();
    }
}
