package com.consolefire.relayer.msgsrc.data.repo.jdbc;

import com.consolefire.relayer.msgsrc.data.entity.MessageSource;
import com.consolefire.relayer.util.data.RowMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import lombok.SneakyThrows;

public class MessageSourceRowMapper implements RowMapper<MessageSource> {

    private final ObjectMapper objectMapper;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC);

    public MessageSourceRowMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    @SneakyThrows
    public MessageSource mapRow(ResultSet resultSet) throws SQLException {
        return MessageSource.builder()
            .identifier(resultSet.getString("identifier"))
            .configuration(objectMapper.readTree(resultSet.getString("configuration")))
            .state(MessageSource.State.valueOf(resultSet.getString("state")))
            .createdAt(Instant.from(FORMATTER.parse(resultSet.getString("created_at"))))
            .updatedAt(Instant.from(FORMATTER.parse(resultSet.getString("updated_at"))))
            .build();
    }
}
