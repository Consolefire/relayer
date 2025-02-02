package com.consolefire.relayer.model.validation;

import com.consolefire.relayer.util.validation.ValidationResult;
import com.consolefire.relayer.util.validation.Validator;

public interface MessageSequenceValidator extends Validator<Long> {

    long ZERO = 0;

    default ValidationResult validate(Long value) {
        boolean isValid = null != value && (value > ZERO && value <= Long.MAX_VALUE - 1);
        return ValidationResult.builder(this, value)
            .withTest(() -> isValid)
            .build();
    }
}
