package com.consolefire.relayer.core.data.repository;

import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.model.ParkedMessage;
import java.io.Serializable;

public interface ParkedMessageRepository<ID extends Serializable, M extends Message<ID>, PM extends ParkedMessage<ID, M>> {

}
