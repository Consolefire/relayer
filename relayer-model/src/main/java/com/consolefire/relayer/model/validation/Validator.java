package com.consolefire.relayer.model.validation;

import java.util.UUID;

@FunctionalInterface
public interface Validator<T> {

    default UUID getId() {
        return UUID.randomUUID();
    }

    ValidationResult validate(T value);

}
