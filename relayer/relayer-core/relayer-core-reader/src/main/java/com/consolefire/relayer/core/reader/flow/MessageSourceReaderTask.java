package com.consolefire.relayer.core.reader.flow;

import com.consolefire.relayer.core.checkpoint.MessageSourceCheckpoint;
import com.consolefire.relayer.core.reader.MessageSource;
import com.consolefire.relayer.model.Message;
import lombok.Builder;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.Callable;

@SuperBuilder
public abstract class MessageSourceReaderTask<
        ID extends Serializable,
        M extends Message<ID>,
        S extends MessageSource<ID, M>,
        R extends MessageSourceReaderTaskResult<ID, M>
        > implements Callable<R> {

    protected final S messageSource;
    protected final MessageSourceCheckpoint messageSourceCheckpoint;

    public MessageSourceReaderTask(S messageSource, MessageSourceCheckpoint messageSourceCheckpoint) {
        this.messageSource = messageSource;
        this.messageSourceCheckpoint = messageSourceCheckpoint;
    }

    @Override
    public R call() throws Exception {
        return buildResult(executeRead());
    }

    protected abstract R buildResult(Collection<M> messages);

    protected Collection<M> executeRead() {
        return null;
    }
}
