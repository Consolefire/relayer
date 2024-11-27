package com.consolefire.relayer.model.validation;

import lombok.Getter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public final class ValidationResult {
    private static final boolean VALID_STATE = true;
    private static final boolean INVALID_STATE = false;

    @Getter
    private final UUID validatorId;
    @Getter
    private final boolean valid;
    private final Map<String, Object> errors;


    private ValidationResult(UUID validatorId, boolean isValid, Map<String, Object> errors) {
        this.validatorId = validatorId;
        this.valid = isValid;
        this.errors = errors;
    }

    public static ValidationResult and(ValidationResult... otherResults) {
        if (null == otherResults || otherResults.length == 0) {
            return null;
        }
        boolean isInvalid = Arrays.stream(otherResults).map(ValidationResult::isValid)
                .anyMatch(state -> INVALID_STATE == state);

        final Map<String, Object> combinedErrors = new HashMap<>();
        Arrays.stream(otherResults).map(ValidationResult::getErrors)
                .forEachOrdered(combinedErrors::putAll);
        return new ValidationResult(UUID.randomUUID(), isInvalid, combinedErrors);
    }

    public static ValidationResult or(ValidationResult... otherResults) {
        if (null == otherResults || otherResults.length == 0) {
            return null;
        }
        boolean isValid = Arrays.stream(otherResults).map(ValidationResult::isValid)
                .anyMatch(state -> VALID_STATE == state);

        final Map<String, Object> combinedErrors = new HashMap<>();
        Arrays.stream(otherResults).map(ValidationResult::getErrors)
                .forEachOrdered(combinedErrors::putAll);
        return new ValidationResult(UUID.randomUUID(), isValid, combinedErrors);
    }

    public static class ValidationResultBuilder {

        private final AtomicBoolean validationStateUpdated = new AtomicBoolean(false);
        private final AtomicBoolean validationState = new AtomicBoolean(VALID_STATE);
        private final Validator<?> validator;
        private final AtomicBoolean testResult = new AtomicBoolean(VALID_STATE);
        private final Map<String, Object> errors = new ConcurrentHashMap<>();

        public ValidationResultBuilder() {
            this.validator = null;
        }

        public ValidationResultBuilder(Validator<?> validator) {
            this.validator = validator;
        }

        public ValidationResultBuilder withError(String key) {
            this.errors.put(key, "");
            if (!validationStateUpdated.get()) {
                validationState.set(INVALID_STATE);
                validationStateUpdated.set(true);
            }
            return this;
        }

        public <R> ValidationResultBuilder withError(String key, R result) {
            this.errors.put(key, result);
            if (!validationStateUpdated.get()) {
                validationState.set(INVALID_STATE);
                validationStateUpdated.set(true);
            }
            return this;
        }

        public synchronized ValidationResultBuilder withTest(Supplier<Boolean> booleanSupplier) {
            this.testResult.set(booleanSupplier.get());
            if (!validationStateUpdated.get()) {
                validationState.set(this.testResult.get());
                validationStateUpdated.set(true);
            }
            return this;
        }

        private void updateValidationState() {
            if(validationStateUpdated.get()){

            }
        }

        public ValidationResult build() {
            boolean isValid = VALID_STATE;
            if (INVALID_STATE == testResult.get()) {
                isValid = INVALID_STATE;
            }

            ValidationResult validationResult = new ValidationResult(
                    Optional.ofNullable(this.validator).map(Validator::getId).orElse(UUID.randomUUID()), isValid, this.errors);

            return validationResult;
        }


    }

    public static ValidationResultBuilder builder(Validator<?> validator) {
        return new ValidationResultBuilder(validator);
    }

    public boolean hasErrors() {
        return !valid && !errors.isEmpty();
    }

    public Map<String, Object> getErrors() {
        return Collections.unmodifiableMap(errors);
    }

}
