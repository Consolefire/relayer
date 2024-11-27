package com.consolefire.relayer.writer;

import com.consolefire.relayer.model.outbox.OutboundMessage;
import com.consolefire.relayer.model.helper.MessageCopier;
import com.consolefire.relayer.model.validation.MessageValidator;
import com.consolefire.relayer.writer.interceptor.AfterWriteInterceptor;
import com.consolefire.relayer.writer.interceptor.BeforeWriteInterceptor;

import java.io.Serializable;
import java.util.Optional;

public abstract class AbstractOutboundMessageWriter<ID extends Serializable> extends AbstractMessageWriter<ID, OutboundMessage<ID>> {


    public AbstractOutboundMessageWriter(
            Optional<MessageValidator<ID, OutboundMessage<ID>>> messageMessageValidator,
            Optional<BeforeWriteInterceptor<ID, OutboundMessage<ID>>> messageBeforeWriteInterceptor,
            Optional<AfterWriteInterceptor<ID, OutboundMessage<ID>>> messageAfterWriteInterceptor,
            Optional<MessageCopier<ID, OutboundMessage<ID>>> messageMessageCopier) {
        super(messageMessageValidator, messageBeforeWriteInterceptor, messageAfterWriteInterceptor, messageMessageCopier);
    }

    @Override
    protected void processException(OutboundMessage<ID> message, Exception exception) {
        throw new RuntimeException(exception);
    }
}
