package com.consolefire.relayer.core.checkpoint.repository.impl;

import static com.consolefire.relayer.core.checkpoint.repository.ReaderCheckpointQueryConstants.COLUMN_CREATED_AT;
import static com.consolefire.relayer.core.checkpoint.repository.ReaderCheckpointQueryConstants.COLUMN_EXPIRES_AT;
import static com.consolefire.relayer.core.checkpoint.repository.ReaderCheckpointQueryConstants.COLUMN_IDENTIFIER;
import static com.consolefire.relayer.core.checkpoint.repository.ReaderCheckpointQueryConstants.COLUMN_IS_COMPLETED;

import com.consolefire.relayer.core.checkpoint.ReaderCheckpoint;
import com.consolefire.relayer.core.checkpoint.repository.CheckpointRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

public class ReaderCheckpointRowMapper implements CheckpointRowMapper<String, ReaderCheckpoint> {

    @Override
    public ReaderCheckpoint mapRow(ResultSet resultSet) throws SQLException {
        ReaderCheckpoint checkpoint = ReaderCheckpoint.builder()
            .identifier(resultSet.getNString(COLUMN_IDENTIFIER))
            .completed(resultSet.getBoolean(COLUMN_IS_COMPLETED))
            .createdAt(Optional.ofNullable(resultSet.getTimestamp(COLUMN_CREATED_AT))
                .map(Timestamp::toInstant).orElse(null))
            .expiresAt(Optional.ofNullable(resultSet.getTimestamp(COLUMN_EXPIRES_AT))
                .map(Timestamp::toInstant).orElse(null))
            .build();
        return checkpoint;
    }
}

