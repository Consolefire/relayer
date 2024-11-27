package com.consolefire.relayer.model.helper;

import java.util.UUID;

public class RandomUuidMessageIdGenerator implements MessageIdGenerator<UUID> {
    @Override
    public UUID generate() {
        return UUID.randomUUID();
    }

}
