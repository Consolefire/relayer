package com.consolefire.relayer.core.checkpoint.repository.impl;

import static com.consolefire.relayer.core.checkpoint.repository.ReaderCheckpointQueryConstants.DELETE_ALL_SQL;
import static com.consolefire.relayer.core.checkpoint.repository.ReaderCheckpointQueryConstants.DELETE_BY_ID_SET_SQL;
import static com.consolefire.relayer.core.checkpoint.repository.ReaderCheckpointQueryConstants.DELETE_BY_ID_SQL;
import static com.consolefire.relayer.core.checkpoint.repository.ReaderCheckpointQueryConstants.INSERT_SQL;
import static com.consolefire.relayer.core.checkpoint.repository.ReaderCheckpointQueryConstants.SELECT_ALL_SQL;
import static com.consolefire.relayer.core.checkpoint.repository.ReaderCheckpointQueryConstants.SELECT_BY_ID_SQL;
import static com.consolefire.relayer.core.checkpoint.repository.ReaderCheckpointQueryConstants.UPDATE_SQL;

import com.consolefire.relayer.core.checkpoint.repository.CheckpointQueryProvider;

public class DefaultReaderCheckpointQueryProvider implements CheckpointQueryProvider {

    @Override
    public String queryForFindAll() {
        return SELECT_ALL_SQL;
    }

    @Override
    public String queryForFindByIdentifier() {
        return SELECT_BY_ID_SQL;
    }

    @Override
    public String queryForInsert() {
        return INSERT_SQL;
    }

    @Override
    public String queryForUpdate() {
        return UPDATE_SQL;
    }

    @Override
    public String queryForDeleteByIdentifier() {
        return DELETE_BY_ID_SQL;
    }

    @Override
    public String queryForDeleteAllByIdentifiers() {
        return DELETE_BY_ID_SET_SQL;
    }

    @Override
    public String queryForDeleteAll() {
        return DELETE_ALL_SQL;
    }
}
