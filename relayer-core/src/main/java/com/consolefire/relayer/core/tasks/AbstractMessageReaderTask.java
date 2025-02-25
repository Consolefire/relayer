package com.consolefire.relayer.core.tasks;

import com.consolefire.relayer.core.reader.MessageReader;
import com.consolefire.relayer.model.Message;
import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AbstractMessageReaderTask<ID extends Serializable, M extends Message<ID>>
    implements MessageReaderTask<ID, M> {

    private final MessageReader<ID, M> messageReader;

    @Override
    public void execute() {
        log.info("Execute message reader task");
    }
}
