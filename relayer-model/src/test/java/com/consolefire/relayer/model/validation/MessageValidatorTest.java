package com.consolefire.relayer.model.validation;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.consolefire.relayer.model.MessageState;
import com.consolefire.relayer.util.validation.ValidationResult;
import com.consolefire.relayer.util.validation.Validator;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MessageValidatorTest {

    private Validator<SimpleTestMessage> messageValidator = new SimpleTestMessageValidator();

    @Test
    void shouldValidateMessageWithValidators() {
        SimpleTestMessage invalidMessage = new SimpleTestMessage();
        SimpleTestMessage validMessage = SimpleTestMessage.builder()
            .messageId(1001l)
            .messageSequence(10l)
            .groupId("G1")
            .createdAt(Instant.now())
            .state(MessageState.NEW)
            .build();
        ValidationResult validationResultOfInvalidMessage = messageValidator.validate(invalidMessage);
        ValidationResult validationResultOfValidMessage = messageValidator.validate(validMessage);

        assertAll(
            () -> assertAll(
                () -> assertNotNull(validationResultOfInvalidMessage),
                () -> assertFalse(validationResultOfInvalidMessage.isValid())
            ),
            () -> assertAll(
                () -> assertNotNull(validationResultOfValidMessage),
                () -> assertTrue(validationResultOfValidMessage.isValid())
            )
        );
    }

    @ParameterizedTest
    @ValueSource(longs = {-1l, 0, Long.MAX_VALUE})
    void shouldBeInvalidMessageSequence(Long sequence) {
        ValidationResult result = new DefaultMessageSequenceValidator()
            .validate(sequence);
        assertFalse(result.isValid());
    }

    @Test
    void shouldBeInvalidMessageSequenceForNull() {
        ValidationResult result = new DefaultMessageSequenceValidator()
            .validate(null);
        assertFalse(result.isValid());
    }

}