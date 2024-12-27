package com.consolefire.relayer.core.checkpoint.repository;

import com.consolefire.relayer.core.checkpoint.Checkpoint;
import com.consolefire.relayer.core.data.RowMapper;
import java.io.Serializable;

public interface CheckpointRowMapper<ID extends Serializable, C extends Checkpoint<ID>>
    extends RowMapper<C> {


}
