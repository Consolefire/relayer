package com.consolefire.relayer.writer;

import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.model.source.MessageSourceProperties;
import java.io.Serializable;

public interface MessageWriter<ID extends Serializable, M extends Message<ID>> {

    long insert(MessageSourceProperties messageSourceProperties, M message);

}
