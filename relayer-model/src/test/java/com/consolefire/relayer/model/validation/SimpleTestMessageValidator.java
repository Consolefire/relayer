package com.consolefire.relayer.model.validation;


import com.consolefire.relayer.util.validation.NotNullValidator;
import com.consolefire.relayer.util.validation.ValidationResult;
import com.consolefire.relayer.util.validation.Validator;
import com.consolefire.relayer.util.validation.Validators;

public class SimpleTestMessageValidator implements Validator<SimpleTestMessage> {

    @Override
    public ValidationResult validate(SimpleTestMessage message) {
        ValidationResult result = Validators.startWith(new NotNullValidator<>(), message)
            .and(new DefaultMessageIdValidator<>(), message.getMessageId())
            .and(new DefaultMessageSequenceValidator(), message.getMessageSequence())
            .validateAll();
        return result;
    }
}
