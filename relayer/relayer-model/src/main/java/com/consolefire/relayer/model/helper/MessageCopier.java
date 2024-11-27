package com.consolefire.relayer.model.helper;

import com.consolefire.relayer.model.Message;

import java.io.Serializable;

public interface MessageCopier<ID extends Serializable, M extends Message<ID>> {

    M copy(M message);

}
