package com.consolefire.relayer.core.reader;

import com.consolefire.relayer.core.data.MessageReadQueryProvider;
import com.consolefire.relayer.core.data.MessageRowMapper;
import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.model.source.InvalidMessageSourceProperties;
import com.consolefire.relayer.model.source.MessageSourceProperties;
import com.consolefire.relayer.util.data.DataAccessException;
import com.consolefire.relayer.util.data.DataSourceResolver;
import com.consolefire.relayer.util.data.InvalidDataSourceException;
import com.consolefire.relayer.util.data.InvalidSqlException;
import com.consolefire.relayer.util.data.PreparedStatementSetter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class BaseMessageReader<ID extends Serializable, M extends Message<ID>>
    implements MessageReader<ID, M> {

    private final DataSourceResolver dataSourceResolver;
    private final MessageReadQueryProvider messageReadQueryProvider;
    private final PreparedStatementSetter<MessageFilterProperties> preparedStatementSetter;
    private final MessageRowMapper<ID, M> messageRowMapper;

    @Override
    public Collection<M> read(MessageSourceProperties messageSourceProperties,
        Optional<MessageFilterProperties> filterPropertiesOptional) {
        if (null == messageSourceProperties || null == messageSourceProperties.getIdentifier()
            || messageSourceProperties.getIdentifier().isBlank()) {
            throw new InvalidMessageSourceProperties("Message source undefined");
        }
        MessageFilterProperties messageFilterProperties = filterPropertiesOptional.orElse(
            MessageFilterProperties.DEFAULT);
        DataSource dataSource = dataSourceResolver.resolve(messageSourceProperties.getIdentifier());
        if (null == dataSource) {
            throw new InvalidDataSourceException(
                "Could not resolve datasource for source: " + messageSourceProperties.getIdentifier());
        }
        String readSql = messageReadQueryProvider.getReadQuery();
        log.debug("Using sql: {}", readSql);
        if (null == readSql || readSql.isBlank()) {
            throw new InvalidSqlException("Invalid read SQL: null/empty");
        }
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(readSql)) {
            preparedStatementSetter.setValues(messageFilterProperties, statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                Collection<M> mCollection = new ArrayList<>();
                while (resultSet.next()) {
                    M message = messageRowMapper.mapRow(resultSet);
                    mCollection.add(message);
                }
                return mCollection;
            } catch (SQLException e) {
                throw new DataAccessException("Failed to execute Query", e);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }


}
