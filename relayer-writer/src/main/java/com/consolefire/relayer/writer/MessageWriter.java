package com.consolefire.relayer.writer;

import com.consolefire.relayer.model.Message;

import com.consolefire.relayer.model.source.MessageSourceProperties;
import java.io.Serializable;

public interface MessageWriter<ID extends Serializable, M extends Message<ID>> {

    <S extends M> S write(MessageSourceProperties messageSourceProperties, M message);

}
