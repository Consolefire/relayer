package com.consolefire.relayer.outbox.model.validation;

import com.consolefire.relayer.model.validation.MessageIdValidator;
import com.consolefire.relayer.model.validation.MessageValidator;
import com.consolefire.relayer.model.validation.ValidationResult;
import com.consolefire.relayer.outbox.model.OutboundMessage;
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
        if (null == message) {
            return ValidationResult.builder(this)
                    .withError("message is null").build();
        }

        return ValidationResult.and(
                messageIdValidator.validate(message.getMessageId()),
                ValidationResult.builder(this)
                        .withTest(() -> null == message.getChannelName() || message.getChannelName().trim().isEmpty())
                        .withError("no_channel", "channelName is null or empty")
                        .build()
        );
    }
}
