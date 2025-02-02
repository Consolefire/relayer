package com.consolefire.relayer.model.validation;

import com.consolefire.relayer.model.Message;

import com.consolefire.relayer.util.validation.Validator;
import java.io.Serializable;

public interface MessageValidator<ID extends Serializable, M extends Message<ID>> extends Validator<M> {

}
