package com.consolefire.relayer.core.data.repository;

import com.consolefire.relayer.model.Message;
import java.io.Serializable;

public abstract class AbstractMessageWriteRepository<ID extends Serializable, M extends Message<ID>>
    implements MessageWriteRepository<ID, M> {

}
