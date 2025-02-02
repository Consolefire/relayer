package com.consolefire.relayer.model.validation;

import com.consolefire.relayer.util.validation.NotNullValidator;
import java.io.Serializable;

public class DefaultMessageIdValidator<ID extends Serializable> extends NotNullValidator<ID>
    implements MessageIdValidator<ID> {

}
