package com.consolefire.relayer.outbox.writer.validation;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.consolefire.relayer.model.validation.DefaultMessageIdValidator;
import com.consolefire.relayer.model.validation.MessageValidator;
import com.consolefire.relayer.outbox.model.OutboundMessage;
import com.consolefire.relayer.util.validation.ValidationResult;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class DefaultOutboundMessageValidatorTest {

    private final MessageValidator<UUID, OutboundMessage<UUID>> messageValidator
        = new DefaultOutboundMessageValidator<>(new DefaultMessageIdValidator<>());

    @Test
    void shouldValidateOutboundMessage() {
        OutboundMessage<UUID> message = new OutboundMessage<>();
        message.setMessageId(UUID.randomUUID());
        message.setMessageSequence(1001L);
        message.setChannelName("test-channel");
        message.setGroupId(UUID.randomUUID().toString());
        ValidationResult result = messageValidator.validate(message);
        assertAll(
            () -> assertNotNull(result),
            () -> assertTrue(result.isValid())
        );
    }

}