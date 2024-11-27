package com.consolefire.relayer.model.helper;

import java.util.Random;

public class RandomLongMessageIdGenerator implements MessageIdGenerator<Long> {

    @Override
    public Long generate() {
        return new Random().nextLong();
    }
}
