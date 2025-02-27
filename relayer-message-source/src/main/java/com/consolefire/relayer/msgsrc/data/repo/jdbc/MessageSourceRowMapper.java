package com.consolefire.relayer.msgsrc.data.repo.jdbc;

import com.consolefire.relayer.msgsrc.data.entity.MessageSource;
import com.consolefire.relayer.msgsrc.data.query.MessageSourceColumnDefinitionProvider;
import com.consolefire.relayer.util.converter.Converter;
import com.consolefire.relayer.util.data.RowMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import lombok.SneakyThrows;

public class MessageSourceRowMapper implements RowMapper<MessageSource> {

    private final MessageSourceColumnDefinitionProvider columnDefinitionProvider;
    private final Converter<String, JsonNode> configurationJsonConverter;

    public MessageSourceRowMapper(MessageSourceColumnDefinitionProvider columnDefinitionProvider,
        Converter<String, JsonNode> configurationJsonConverter) {
        this.columnDefinitionProvider = columnDefinitionProvider;
        this.configurationJsonConverter = configurationJsonConverter;
    }

    @Override
    @SneakyThrows
    public MessageSource mapRow(ResultSet resultSet) throws SQLException {
        JsonNode configuration = configurationJsonConverter.convert(
            resultSet.getString(columnDefinitionProvider.getConfigurationColumnDefinition().columnName()));
        return MessageSource.builder()
            .identifier(Optional.ofNullable(
                    resultSet.getString(columnDefinitionProvider.getIdentifierColumnDefinition().columnName()))
                .orElse(null))
            .configuration(configuration)
            .state(Optional.ofNullable(
                    resultSet.getString(columnDefinitionProvider.getStateColumnDefinition().columnName()))
                .map(MessageSource.State::valueOf)
                .orElse(null))
            .createdAt(Optional.ofNullable(
                    resultSet.getTimestamp(columnDefinitionProvider.getCreatedAtColumnDefinition().columnName()))
                .map(Timestamp::toInstant)
                .orElse(null))
            .updatedAt(Optional.ofNullable(
                    resultSet.getTimestamp(columnDefinitionProvider.getUpdatedAtColumnDefinition().columnName()))
                .map(Timestamp::toInstant)
                .orElse(null))
            .build();
    }
}