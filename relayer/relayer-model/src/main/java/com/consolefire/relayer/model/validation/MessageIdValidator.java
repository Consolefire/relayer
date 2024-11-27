package com.consolefire.relayer.model.validation;

import java.io.Serializable;

@FunctionalInterface
public interface MessageIdValidator<ID extends Serializable> extends Validator<ID> {

    ValidationResult validate(ID id);

}
