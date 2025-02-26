package com.consolefire.relayer.msgsrc.data.repo.jdbc;

import com.consolefire.relayer.msgsrc.data.entity.MessageSource;
import com.consolefire.relayer.msgsrc.data.query.MessageSourcePreparedStatementSetters;
import com.consolefire.relayer.msgsrc.data.query.MessageSourceQueryProvider;
import com.consolefire.relayer.msgsrc.data.repo.MessageSourceRepository;
import com.consolefire.relayer.util.data.PreparedStatementSetter;
import com.consolefire.relayer.util.data.RowMapper;
import com.consolefire.relayer.util.data.tx.TransactionSupport;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageSourceDefaultJdbcRepository implements MessageSourceRepository, TransactionSupport {

    private final MessageSourceQueryProvider messageSourceQueryProvider;
    private final DataSource dataSource;
    private final ObjectMapper objectMapper;
    private final RowMapper<MessageSource> rowMapper;


    public MessageSourceDefaultJdbcRepository(MessageSourceQueryProvider messageSourceQueryProvider,
        DataSource dataSource, ObjectMapper objectMapper) {
        this.messageSourceQueryProvider = messageSourceQueryProvider;
        this.dataSource = dataSource;
        this.objectMapper = objectMapper;
        this.rowMapper = new MessageSourceRowMapper(objectMapper);
    }

    @Override
    public MessageSource findById(String identifier) {
        String sql = messageSourceQueryProvider.getFindByIdQuery();
        PreparedStatementSetter<String> setter = MessageSourcePreparedStatementSetters.findByIdSetter();

        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            setter.setValues(identifier, preparedStatement);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return rowMapper.mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            log.error("Error finding MessageSource by id: {}", identifier, e);
        }
        return null;
    }

    @Override
    public int saveOrUpdate(MessageSource messageSource) {
        String sql = messageSourceQueryProvider.getSaveOrUpdateQuery();
        PreparedStatementSetter<MessageSource> setter = MessageSourcePreparedStatementSetters.saveOrUpdateSetter(
            objectMapper);

        return executeTransaction(dataSource, connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                setter.setValues(messageSource, preparedStatement);
                return preparedStatement.executeUpdate();
            }
        }, e -> log.error("Error saving/updating MessageSource: {}", messageSource.getIdentifier(), e));
    }

    @Override
    public int updateState(String id, MessageSource.State state) {
        String sql = messageSourceQueryProvider.getUpdateStateQuery();
        MessageSourcePreparedStatementSetters.StateUpdate stateUpdate = new MessageSourcePreparedStatementSetters.StateUpdate();
        stateUpdate.identifier = id;
        stateUpdate.state = state;
        PreparedStatementSetter<MessageSourcePreparedStatementSetters.StateUpdate> setter = MessageSourcePreparedStatementSetters.updateStateSetter();

        return executeTransaction(dataSource, connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                setter.setValues(stateUpdate, preparedStatement);
                return preparedStatement.executeUpdate();
            }
        }, e -> log.error("Error updating MessageSource state: {}", id, e));
    }

    @Override
    public int updateConfiguration(String id, JsonNode configuration) {
        String sql = messageSourceQueryProvider.getUpdateConfigurationQuery();
        MessageSourcePreparedStatementSetters.ConfigUpdate configUpdate = new MessageSourcePreparedStatementSetters.ConfigUpdate();
        configUpdate.identifier = id;
        configUpdate.configuration = configuration;
        PreparedStatementSetter<MessageSourcePreparedStatementSetters.ConfigUpdate> setter = MessageSourcePreparedStatementSetters.updateConfigurationSetter(
            objectMapper);

        return executeTransaction(dataSource, connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                setter.setValues(configUpdate, preparedStatement);
                return preparedStatement.executeUpdate();
            }
        }, e -> log.error("Error updating MessageSource configuration: {}", id, e));
    }

    @Override
    public List<MessageSource> findAll() {
        String sql = messageSourceQueryProvider.getFindAllQuery();
        PreparedStatementSetter<Void> setter = MessageSourcePreparedStatementSetters.findAllSetter();
        List<MessageSource> messageSources = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                messageSources.add(rowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            log.error("Error finding all MessageSources", e);
        }
        return messageSources;
    }
}