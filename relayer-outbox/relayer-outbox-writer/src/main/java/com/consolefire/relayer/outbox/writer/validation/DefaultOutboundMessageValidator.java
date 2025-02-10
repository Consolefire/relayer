package com.consolefire.relayer.outbox.writer.validation;

import com.consolefire.relayer.model.validation.DefaultMessageSequenceValidator;
import com.consolefire.relayer.model.validation.MessageIdValidator;
import com.consolefire.relayer.model.validation.MessageValidator;
import com.consolefire.relayer.outbox.model.OutboundMessage;
import com.consolefire.relayer.util.validation.ValidationResult;
import com.consolefire.relayer.util.validation.Validators;
import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DefaultOutboundMessageValidator<ID extends Serializable, M extends OutboundMessage<ID>>
    implements MessageValidator<ID, M> {

    private final MessageIdValidator<ID> messageIdValidator;

    @Override
    public ValidationResult validate(M message) {
        return Validators.startWith(Validators.NOT_NULL_VALIDATOR, message)
            .and(messageIdValidator, message.getMessageId())
            .and(new DefaultMessageSequenceValidator(), message.getMessageSequence())
            .and(Validators.NOT_BLANK_VALIDATOR, message.getChannelName())
            .validateAll();
    }
}
