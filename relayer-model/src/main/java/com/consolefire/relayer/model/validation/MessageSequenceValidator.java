package com.consolefire.relayer.model.validation;

public interface MessageSequenceValidator extends Validator<Long> {

    long ZERO = 0;

    default ValidationResult validate(Long value) {
        return ValidationResult.builder(this).withTest(
                        () -> null != value && value > ZERO && value < Long.MAX_VALUE)
                .withError("out-of-range", "Sequence value if out of range.")
                .build();
    }
}
