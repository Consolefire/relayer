package com.consolefire.relayer.util.validation;

import java.util.UUID;

@FunctionalInterface
public interface Validator<T> {

    default UUID getId() {
        return UUID.randomUUID();
    }

    default String getName() {
        return getClass().getCanonicalName();
    }

    ValidationResult validate(T value);

}
