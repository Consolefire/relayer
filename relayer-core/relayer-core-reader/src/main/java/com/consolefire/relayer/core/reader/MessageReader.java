package com.consolefire.relayer.core.reader;

import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.model.source.MessageSourceProperties;
import java.io.Serializable;
import java.util.Collection;

public interface MessageReader<ID extends Serializable, M extends Message<ID>> {

    Collection<M> read(MessageSourceProperties messageSource);

}
