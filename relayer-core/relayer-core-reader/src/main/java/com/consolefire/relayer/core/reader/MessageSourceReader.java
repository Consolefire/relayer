package com.consolefire.relayer.core.reader;

import com.consolefire.relayer.model.Message;

import java.io.Serializable;
import java.util.Collection;

public interface MessageSourceReader<ID extends Serializable, M extends Message<ID>> {

    Collection<M> read(MessageSource messageSource);

}
