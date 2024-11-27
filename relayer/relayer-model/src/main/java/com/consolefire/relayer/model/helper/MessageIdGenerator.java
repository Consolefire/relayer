package com.consolefire.relayer.model.helper;

import java.io.Serializable;

public interface MessageIdGenerator<ID extends Serializable> {

    ID generate();

}
