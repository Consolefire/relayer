package com.consolefire.relayer.core.checkpoint.repository.impl;

import com.consolefire.relayer.core.checkpoint.ReaderCheckpoint;
import com.consolefire.relayer.core.checkpoint.repository.CheckpointQueryProvider;
import com.consolefire.relayer.core.checkpoint.repository.ReaderCheckpointRepository;
import com.consolefire.relayer.util.data.PreparedStatementSetter;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractReaderCheckpointRepository //
    extends AbstractCheckpointRepository<String, ReaderCheckpoint> //
    implements ReaderCheckpointRepository {

    public AbstractReaderCheckpointRepository(
        CheckpointQueryProvider checkpointQueryProvider) {
        super(checkpointQueryProvider, new ReaderCheckpointRowMapper());
    }

    @Override
    protected PreparedStatementSetter<ReaderCheckpoint> getInsertStatementSetter() {
        return (checkpoint, statement) -> {
            statement.setNString(1, checkpoint.getIdentifier());
            statement.setBoolean(2, checkpoint.isCompleted());
            statement.setTimestamp(3, //
                Optional.ofNullable(checkpoint.getCreatedAt()) //
                    .map(Timestamp::from) //
                    .orElse(Timestamp.from(Instant.now())));
            statement.setTimestamp(4, //
                Optional.ofNullable(checkpoint.getExpiresAt()) //
                    .map(Timestamp::from) //
                    .orElse(null));
        };
    }

    @Override
    protected PreparedStatementSetter<ReaderCheckpoint> getUpdateStatementSetter() {
        return (checkpoint, statement) -> {
            statement.setBoolean(1, checkpoint.isCompleted());
            statement.setTimestamp(2, //
                Optional.ofNullable(checkpoint.getCreatedAt()) //
                    .map(Timestamp::from) //
                    .orElse(Timestamp.from(Instant.now())));
            statement.setTimestamp(3, //
                Optional.ofNullable(checkpoint.getExpiresAt()) //
                    .map(Timestamp::from) //
                    .orElse(null));
            statement.setNString(4, checkpoint.getIdentifier());
        };
    }

    @Override
    protected PreparedStatementSetter<String> getCheckpointIdStatementSetter() {
        return (id, statement) -> statement.setNString(1, id);
    }

    @Override
    protected PreparedStatementSetter<Set<String>> getCheckpointIdsStatementSetter() {
        return (idSet, statement) -> {
            if (idSet == null || idSet.isEmpty()) {
                statement.setObject(1, "");
            } else {
                statement.setObject(1,
                    idSet.stream().collect(Collectors.joining(",", "'", ",")));
            }
        };
    }
}
