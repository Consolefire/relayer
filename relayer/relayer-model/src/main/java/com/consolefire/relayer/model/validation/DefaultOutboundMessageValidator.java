package com.consolefire.relayer.model.validation;

import com.consolefire.relayer.model.OutboundMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@RequiredArgsConstructor
public class DefaultOutboundMessageValidator<ID extends Serializable, M extends OutboundMessage<ID>>
        implements MessageValidator<ID, M> {

    private final MessageIdValidator<ID> messageIdValidator;
    private final MessageSequenceValidator messageSequenceValidator;

    @Override
    public ValidationResult validate(M message) {
        if (null == message) {
            return ValidationResult.builder(this)
                    .withError("message is null").build();
        }

        return ValidationResult.and(
                messageIdValidator.validate(message.getMessageId()),
                messageSequenceValidator.validate(message.getMessageSequence()),
                ValidationResult.builder(this)
                        .withTest(() -> null == message.getChannelName() || message.getChannelName().trim().isEmpty())
                        .withError("no_channel", "channelName is null or empty")
                        .build()
        );
    }
}
