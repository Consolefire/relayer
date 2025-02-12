package com.consolefire.relayer.outbox.core.reader;

import com.consolefire.relayer.core.data.MessageReadQueryProvider;
import com.consolefire.relayer.core.data.MessageRowMapper;
import com.consolefire.relayer.core.reader.MessageFilterProperties;
import com.consolefire.relayer.core.reader.MessageReader;
import com.consolefire.relayer.model.source.MessageSourceProperties;
import com.consolefire.relayer.outbox.model.OutboundMessage;
import com.consolefire.relayer.util.data.DataSourceResolver;
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
public abstract class OutboxMessageReader<ID extends Serializable> implements
    MessageReader<ID, OutboundMessage<ID>> {

    private final DataSourceResolver dataSourceResolver;
    private final MessageReadQueryProvider messageReadQueryProvider;
    private final PreparedStatementSetter<MessageFilterProperties> preparedStatementSetter;
    private final MessageRowMapper<ID, OutboundMessage<ID>> messageRowMapper;

    @Override
    public Collection<OutboundMessage<ID>> read(MessageSourceProperties messageSourceProperties,
        Optional<MessageFilterProperties> filterPropertiesOptional) {
        if (null == messageSourceProperties || null == messageSourceProperties.getIdentifier()
            || messageSourceProperties.getIdentifier().isBlank()) {
            throw new RuntimeException("Message source undefined");
        }
        MessageFilterProperties messageFilterProperties = filterPropertiesOptional.orElse(
            MessageFilterProperties.DEFAULT);
        DataSource dataSource = dataSourceResolver.resolve(messageSourceProperties.getIdentifier());
        if (null == dataSource) {
            throw new RuntimeException(
                "Could not resolve datasource for source: " + messageSourceProperties.getIdentifier());
        }
        String readSql = messageReadQueryProvider.getReadQuery();
        log.debug("Using sql: {}", readSql);
        if (null == readSql || readSql.isBlank()) {
            throw new RuntimeException("Invalid read SQL: null/empty");
        }
        Collection<OutboundMessage<ID>> mCollection = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(readSql)) {
            preparedStatementSetter.setValues(messageFilterProperties, statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    OutboundMessage<ID> message = messageRowMapper.mapRow(resultSet);
                    mCollection.add(message);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        return mCollection;
    }


}
