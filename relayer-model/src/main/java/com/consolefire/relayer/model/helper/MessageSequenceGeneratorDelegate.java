package com.consolefire.relayer.model.helper;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MessageSequenceGeneratorDelegate implements MessageSequenceGenerator {

    @Getter
    @NonNull
    private final MessageSequenceGenerator generator;

    @Override
    public Long next() {
        return generator.next();
    }
}
