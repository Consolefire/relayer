package com.consolefire.relayer.util.validation;

public class NotNullValidator<T> implements Validator<T> {

    @Override
    public ValidationResult validate(T value) {
        return ValidationResult.builder(this, value)
            .withTest(() -> null != value)
            .withError("null-value", "input value is null")
            .build();
    }
}
