package com.consolefire.relayer.msgsrc.data.query;


import com.consolefire.relayer.msgsrc.data.entity.MessageSource;
import com.consolefire.relayer.util.data.PreparedStatementSetter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class MessageSourcePreparedStatementSetters {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC);

    public static PreparedStatementSetter<String> findByIdSetter() {
        return (identifier, ps) -> ps.setString(1, identifier);
    }

    public static PreparedStatementSetter<MessageSource> saveOrUpdateSetter(ObjectMapper objectMapper) {
        return (messageSource, ps) -> {
            try {
                ps.setString(1, messageSource.getIdentifier());
                ps.setString(2, objectMapper.writeValueAsString(messageSource.getConfiguration()));
                ps.setString(3, messageSource.getState().toString());
                ps.setString(4, FORMATTER.format(
                    messageSource.getCreatedAt() == null ? Instant.now() : messageSource.getCreatedAt()));
                ps.setString(5, FORMATTER.format(
                    messageSource.getUpdatedAt() == null ? Instant.now() : messageSource.getUpdatedAt()));
                ps.setString(6, objectMapper.writeValueAsString(messageSource.getConfiguration()));
                ps.setString(7, messageSource.getState().toString());
                ps.setString(8, FORMATTER.format(Instant.now()));
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static PreparedStatementSetter<StateUpdate> updateStateSetter() {
        return (stateUpdate, ps) -> {
            ps.setString(1, stateUpdate.state.toString());
            ps.setString(2, FORMATTER.format(Instant.now()));
            ps.setString(3, stateUpdate.identifier);
        };
    }

    public static PreparedStatementSetter<ConfigUpdate> updateConfigurationSetter(ObjectMapper objectMapper) {
        return (configUpdate, ps) -> {
            try {
                ps.setString(1, objectMapper.writeValueAsString(configUpdate.configuration));
                ps.setString(2, FORMATTER.format(Instant.now()));
                ps.setString(3, configUpdate.identifier);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static PreparedStatementSetter<Void> findAllSetter() {
        return (v, ps) -> {
        };
    }

    public static class StateUpdate {

        public String identifier;
        public MessageSource.State state;
    }

    public static class ConfigUpdate {

        public String identifier;
        public JsonNode configuration;
    }
}