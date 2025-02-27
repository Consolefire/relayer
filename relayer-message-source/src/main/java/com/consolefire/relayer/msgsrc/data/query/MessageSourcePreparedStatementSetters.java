package com.consolefire.relayer.msgsrc.data.query;

import com.consolefire.relayer.msgsrc.data.entity.MessageSource;
import com.consolefire.relayer.util.data.PreparedStatementSetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

public class MessageSourcePreparedStatementSetters implements MessageSourcePreparedStatementSetterProvider {

    private final MessageSourceParameterDefinitionProvider parameterDefinitionProvider;
    private final ObjectMapper objectMapper;

    public MessageSourcePreparedStatementSetters(MessageSourceParameterDefinitionProvider parameterDefinitionProvider,
        ObjectMapper objectMapper) {
        this.parameterDefinitionProvider = parameterDefinitionProvider;
        this.objectMapper = objectMapper;
    }

    @Override
    public PreparedStatementSetter<String> findByIdSetter() {
        return (identifier, ps) -> {
            try {
                ps.setString(parameterDefinitionProvider.getIdentifierParameterDefinition().parameterIndex(),
                    identifier);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public PreparedStatementSetter<MessageSource> saveSetter() {
        return (messageSource, ps) -> {
            try {
                ps.setString(parameterDefinitionProvider.getIdentifierParameterDefinition().parameterIndex(),
                    messageSource.getIdentifier());
                ps.setString(parameterDefinitionProvider.getConfigurationParameterDefinition().parameterIndex(),
                    objectMapper.writeValueAsString(messageSource.getConfiguration()));
                ps.setString(parameterDefinitionProvider.getStateParameterDefinition().parameterIndex(),
                    messageSource.getState().toString());
                ps.setTimestamp(parameterDefinitionProvider.getCreatedAtParameterDefinition().parameterIndex(),
                    Timestamp.from(
                        messageSource.getCreatedAt() == null ? Instant.now() : messageSource.getCreatedAt()));
                ps.setTimestamp(parameterDefinitionProvider.getUpdatedAtParameterDefinition().parameterIndex(),
                    Timestamp.from(
                        messageSource.getUpdatedAt() == null ? Instant.now() : messageSource.getUpdatedAt()));

            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public PreparedStatementSetter<MessageSource> updateStateSetter() {
        return (messageSource, ps) -> {
            try {
                ps.setString(parameterDefinitionProvider.getStateParameterDefinition().parameterIndex(),
                    messageSource.getState().toString());
                ps.setTimestamp(parameterDefinitionProvider.getUpdatedAtParameterDefinition().parameterIndex(),
                    Timestamp.from(Instant.now()));
                ps.setString(parameterDefinitionProvider.getIdentifierParameterDefinition().parameterIndex(),
                    messageSource.getIdentifier());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public PreparedStatementSetter<MessageSource> updateConfigurationSetter() {
        return (messageSource, ps) -> {
            try {
                ps.setString(parameterDefinitionProvider.getConfigurationParameterDefinition().parameterIndex(),
                    objectMapper.writeValueAsString(messageSource.getConfiguration()));
                ps.setTimestamp(parameterDefinitionProvider.getUpdatedAtParameterDefinition().parameterIndex(),
                    Timestamp.from(Instant.now()));
                ps.setString(parameterDefinitionProvider.getIdentifierParameterDefinition().parameterIndex(),
                    messageSource.getIdentifier());
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public PreparedStatementSetter<Void> findAllSetter() {
        return (v, ps) -> {
        };
    }
}
