package com.consolefire.relayer.writer;

import com.consolefire.relayer.model.Message;
import java.io.Serializable;

public interface MessageWriteQueryProvider<ID extends Serializable, M extends Message<ID>> {

    String getInsertQuery(M message);

}
