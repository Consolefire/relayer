package com.consolefire.relayer.model.validation;

import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.util.validation.ValidationResult;
import lombok.Builder;

import java.util.Objects;
import java.util.Optional;

import static java.lang.String.format;

public class InvalidMessageException extends RuntimeException {

    private final Message<?> relayMessage;
    private final ValidationResult validationResult;

    @Builder
    public InvalidMessageException(Message<?> relayMessage, ValidationResult validationResult) {
        this.relayMessage = relayMessage;
        this.validationResult = validationResult;
    }

    @Override
    public String getMessage() {
        String formatter = "message with id:[%s] has validation errors: [%s]";
        if (null != relayMessage || null != validationResult) {
            return format(formatter,
                    Optional.ofNullable(relayMessage).map(Message::getMessageId)
                            .map(Objects::toString).orElse("UNKNOWN"),
                    Optional.ofNullable(validationResult).map(ValidationResult::getErrors)
                            .map(Objects::toString)
                            .orElse("NONE"));
        }
        return super.getMessage();
    }
}
