package com.consolefire.relayer.writer.delegate;

import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.writer.MessageWriter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
public class DelegatingMessageWriter<ID extends Serializable, M extends Message<ID>>
        implements MessageWriter<ID, M> {

    private final MessageWriter<ID, M> messageWriterDelegate;

    @Override
    public <S extends M> S write(M message) {
        return messageWriterDelegate.write(message);
    }
}
