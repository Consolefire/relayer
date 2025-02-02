package com.consolefire.relayer.util.validation;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class ValidationResult {

    private static final boolean VALID_STATE = true;
    private static final boolean INVALID_STATE = false;
    private static final String RANDOM_VALIDATOR_NAME = "DYNAMIC-VALIDATOR";

    private final UUID validatorId;
    private final String validatorName;
    private final boolean valid;
    private final Map<String, Object> errors;
    private final Map<String, Object> validatorInput;

    private ValidationResult(UUID validatorId, String validatorName, boolean valid) {
        this(validatorId, validatorName, valid, null, new HashMap<>());
    }

    private ValidationResult(UUID validatorId, String validatorName, boolean valid, Map<String, Object> errors) {
        this(validatorId, validatorName, valid, errors, new HashMap<>());
    }

    private ValidationResult(UUID validatorId, String validatorName, boolean valid, Map<String, Object> errors,
        Map<String, Object> validatorInput) {
        this.validatorId = validatorId;
        this.validatorName = validatorName;
        this.valid = valid;
        this.errors = errors;
        this.validatorInput = validatorInput;
    }

    public static ValidationResult and(ValidationResult... otherResults) {
        if (null == otherResults || otherResults.length == 0) {
            return null;
        }
        Map<String, Object> inputMap = new HashMap<>();
        boolean validState = !(Arrays.stream(otherResults)
            .map(v -> {
                inputMap.put(v.getValidatorId().toString(), v.getValidatorInput());
                return v;
            })
            .map(ValidationResult::isValid)
            .anyMatch(state -> INVALID_STATE == state));

        final Map<String, Object> combinedErrors = new HashMap<>();
        Arrays.stream(otherResults).map(ValidationResult::getErrors)
            .forEachOrdered(combinedErrors::putAll);

        return new ValidationResult(UUID.randomUUID(), "AND", validState, combinedErrors, inputMap);
    }

    public static ValidationResult or(ValidationResult... otherResults) {
        if (null == otherResults || otherResults.length == 0) {
            return null;
        }
        boolean validState = Arrays.stream(otherResults).map(ValidationResult::isValid)
            .anyMatch(state -> VALID_STATE == state);

        final Map<String, Object> combinedErrors = new HashMap<>();
        Arrays.stream(otherResults).map(ValidationResult::getErrors)
            .forEachOrdered(combinedErrors::putAll);
        return new ValidationResult(UUID.randomUUID(), "OR", validState, combinedErrors);
    }

    public static class ValidationResultBuilder {

        private final AtomicBoolean validationStateUpdated = new AtomicBoolean(false);
        private final AtomicBoolean validationState = new AtomicBoolean(VALID_STATE);
        private final AtomicBoolean testResult = new AtomicBoolean(VALID_STATE);
        private final Map<String, Object> errors = new ConcurrentHashMap<>();

        private final Validator<?> validator;
        private final Object source;

        private ValidationResultBuilder(Validator<?> validator, Object source) {
            this.validator = validator;
            this.source = source;
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
            if (validationStateUpdated.get()) {

            }
        }

        public ValidationResult build() {
            boolean isValid = VALID_STATE;
            if (INVALID_STATE == testResult.get()) {
                isValid = INVALID_STATE;
            }
            UUID validatorId = Optional.ofNullable(this.validator).map(Validator::getId).orElse(UUID.randomUUID());
            Map<String, Object> inputMap = new HashMap<>();
            inputMap.put(validatorId.toString(), this.source);
            return new ValidationResult(validatorId, RANDOM_VALIDATOR_NAME, isValid, this.errors, inputMap);
        }


    }


    public static ValidationResultBuilder builder(Validator<?> validator, Object input) {
        return new ValidationResultBuilder(validator, input);
    }

    public boolean hasErrors() {
        return !valid && !errors.isEmpty();
    }

    public Map<String, Object> getErrors() {
        return Collections.unmodifiableMap(errors);
    }

}
