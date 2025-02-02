package com.consolefire.relayer.util.validation;

public class NotBlankStringValidator implements Validator<String> {

    @Override
    public ValidationResult validate(String value) {
        return ValidationResult.builder(this, value)
            .withTest(() -> null != value && !value.isBlank())
            .withError("blank-string")
            .build();
    }
}
