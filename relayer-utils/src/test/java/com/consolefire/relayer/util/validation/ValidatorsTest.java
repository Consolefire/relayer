package com.consolefire.relayer.util.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ValidatorsTest {

    @Test
    void validateInChainWithAnd(){
        boolean expected = (true && true && false && true);
        Validators validators = Validators.startWith(new NotNullValidator<>(), 100l)
            .and(new NotNullValidator<>(), 200l)
            .and(new NotNullValidator<>(), null)
            .and(new NotNullValidator<>(), 300l);
        ValidationResult result = validators.validateAll();
        assertEquals(expected, result.isValid());
    }

    @Test
    void validateInChainWithOr(){
        boolean expected = (true || true || false || true);
        Validators validators = Validators.startWith(new NotNullValidator<>(), 100l)
            .or(new NotNullValidator<>(), 200l)
            .or(new NotNullValidator<>(), null)
            .or(new NotNullValidator<>(), 300l);
        ValidationResult result = validators.validateAll();
        assertEquals(expected, result.isValid());
    }

    @Test
    void validateInChainWithMixed(){
        boolean expected = (true && true || false && true);
        Validators validators = Validators.startWith(new NotNullValidator<>(), 100l)
            .and(new NotNullValidator<>(), 200l)
            .or(new NotNullValidator<>(), null)
            .and(new NotNullValidator<>(), 300l);
        ValidationResult result = validators.validateAll();
        assertEquals(expected, result.isValid());
    }

}