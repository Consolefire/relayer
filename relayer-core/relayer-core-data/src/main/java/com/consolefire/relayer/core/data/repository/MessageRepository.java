package com.consolefire.relayer.core.data.repository;

import com.consolefire.relayer.model.Message;
import java.io.Serializable;

public interface MessageRepository<ID extends Serializable, M extends Message<ID>> {


}
