package com.consolefire.relayer.writer;

import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.model.helper.MessageCopier;
import com.consolefire.relayer.model.validation.InvalidMessageException;
import com.consolefire.relayer.model.validation.MessageValidator;
import com.consolefire.relayer.model.validation.ValidationResult;
import com.consolefire.relayer.writer.interceptor.AfterWriteInterceptor;
import com.consolefire.relayer.writer.interceptor.BeforeWriteInterceptor;

import java.io.Serializable;
import java.util.Optional;

public abstract class AbstractMessageWriter<ID extends Serializable, M extends Message<ID>> implements MessageWriter<ID, M> {

    protected final Optional<MessageValidator<ID, M>> messageValidator;
    protected final Optional<BeforeWriteInterceptor<ID, M>> beforeWriteInterceptor;
    protected final Optional<AfterWriteInterceptor<ID, M>> afterWriteInterceptor;
    protected final Optional<MessageCopier<ID, M>> messageCopier;

    public AbstractMessageWriter(
            Optional<MessageValidator<ID, M>> messageValidator,
            Optional<BeforeWriteInterceptor<ID, M>> beforeWriteInterceptor,
            Optional<AfterWriteInterceptor<ID, M>> afterWriteInterceptor,
            Optional<MessageCopier<ID, M>> messageCopier) {
        this.messageValidator = messageValidator;
        this.beforeWriteInterceptor = beforeWriteInterceptor;
        this.afterWriteInterceptor = afterWriteInterceptor;
        this.messageCopier = messageCopier;
    }

    @Override
    public <S extends M> S write(M message) {
        messageValidator.ifPresent(validator -> {
            ValidationResult validationResult = validator.validate(message);
            if (!validationResult.isValid()) {
                throw InvalidMessageException.builder()
                        .relayMessage(message).validationResult(validationResult)
                        .build();
            }
        });

        beforeWriteInterceptor.ifPresent(interceptor -> interceptor.before(
                messageCopier.map(mc -> mc.copy(message)).orElse(message)));

        S out = null;
        try {
            out = executeWrite(message);
        } catch (Exception exception) {
            processException(message, exception);
        }
        if (out != null) {
            afterWriteInterceptor.ifPresent(interceptor -> interceptor.after(
                    messageCopier.map(mc -> mc.copy(message)).orElse(message)));
        }
        return out;
    }

    protected abstract <S extends M> S executeWrite(M message) throws Exception;

    protected abstract void processException(M message, Exception exception);
}
