package com.consolefire.relayer.model.validation;

import com.consolefire.relayer.util.validation.ValidationResult;
import com.consolefire.relayer.util.validation.Validator;
import java.io.Serializable;

@FunctionalInterface
public interface MessageIdValidator<ID extends Serializable> extends Validator<ID> {

    ValidationResult validate(ID id);

}
