package com.consolefire.relayer.core.checkpoint.repository;

public interface CheckpointQueryProvider {

    String queryForFindAll();

    String queryForFindByIdentifier();

    String queryForInsert();

    String queryForUpdate();

    String queryForDeleteByIdentifier();

    String queryForDeleteAllByIdentifiers();

    String queryForDeleteAll();
}
