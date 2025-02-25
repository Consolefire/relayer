package com.consolefire.relayer.core.tasks;

import com.consolefire.relayer.model.Message;
import java.io.Serializable;

public interface MessageReaderTask<ID extends Serializable, M extends Message<ID>>  {

    void execute();

}
