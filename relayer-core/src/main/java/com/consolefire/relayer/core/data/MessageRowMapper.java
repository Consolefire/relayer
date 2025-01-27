package com.consolefire.relayer.core.data;

import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.util.data.RowMapper;
import java.io.Serializable;

public interface MessageRowMapper<ID extends Serializable, M extends Message<ID>>
    extends RowMapper<M> {


}
