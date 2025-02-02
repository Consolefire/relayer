package com.consolefire.relayer.model.helper;

import java.util.concurrent.atomic.AtomicLong;

public class InMemoryMessageSequenceGenerator implements MessageSequenceGenerator {

    private final AtomicLong atomicLong = new AtomicLong(0);

    @Override
    public Long next() {
        return atomicLong.incrementAndGet();
    }
}
