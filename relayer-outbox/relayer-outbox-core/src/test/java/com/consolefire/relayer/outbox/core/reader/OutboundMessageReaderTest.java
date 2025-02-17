package com.consolefire.relayer.outbox.core.reader;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.consolefire.relayer.core.data.MessageReadQueryProvider;
import com.consolefire.relayer.core.data.MessageRowMapper;
import com.consolefire.relayer.core.reader.MessageFilterProperties;
import com.consolefire.relayer.core.reader.MessageFilterProperties.DefaultMessageFilterProperties;
import com.consolefire.relayer.model.source.InvalidMessageSourceProperties;
import com.consolefire.relayer.model.source.MessageSourceProperties;
import com.consolefire.relayer.outbox.model.OutboundMessage;
import com.consolefire.relayer.testutils.ext.TestLoggerExtension;
import com.consolefire.relayer.testutils.logging.TestLogger;
import com.consolefire.relayer.util.data.DataAccessException;
import com.consolefire.relayer.util.data.DataSourceResolver;
import com.consolefire.relayer.util.data.InvalidDataSourceException;
import com.consolefire.relayer.util.data.InvalidSqlException;
import com.consolefire.relayer.util.data.PreparedStatementSetter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.slf4j.event.Level;

@Slf4j
@TestLogger(level = Level.DEBUG)
@TestInstance(Lifecycle.PER_CLASS)
@TestLoggerExtension
class OutboundMessageReaderTest {

    private static final String READ_SQL = """
        SELECT
            om.message_id,
            om.message_sequence,
            om.group_id,
            om.channel_name,
            om.payload,
            om.headers,
            om.metadata,
            om.state,
            om.attempted_at,
            om.attempt_count,
            om.created_at,
            om.updated_at
        FROM outbound_message om
        LEFT OUTER JOIN sidelined_group sg
            ON (sg.group_id != om.group_id)
        WHERE
            om.state IN ('NEW', 'PARKED', 'RELAY_FAILED')
            AND (om.attempt_count is null or om.attempt_count < ?)
        LIMIT ?""";
    private static final int DEFAULT_LIMIT = 1;
    private static final int MAX_ATTEMPTS = 3;
    private static final String SOURCE_1 = UUID.randomUUID().toString();
    private static final MessageSourceProperties MESSAGE_SOURCE_PROPERTIES
        = MessageSourceProperties.builder().identifier(SOURCE_1).build();

    private DataSourceResolver dataSourceResolver;
    private MessageReadQueryProvider messageReadQueryProvider;
    private PreparedStatementSetter<MessageFilterProperties> preparedStatementSetter;
    private MessageRowMapper<UUID, OutboundMessage<UUID>> messageRowMapper;
    private MessageFilterProperties<UUID> messageFilterProperties;
    private OutboundMessageReader<UUID> outboundMessageReader;

    @BeforeAll
    void init() {

    }


    @SneakyThrows
    void mockForValidMessage() {
        when(messageReadQueryProvider.getReadQuery()).thenReturn(READ_SQL);
        DataSource dataSource = mock(DataSource.class);
        when(dataSourceResolver.resolve(SOURCE_1)).thenReturn(dataSource);
        Connection connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);
        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.prepareStatement(READ_SQL)).thenReturn(statement);
        doNothing().when(preparedStatementSetter).setValues(messageFilterProperties, statement);
        ResultSet resultSet = mock(ResultSet.class);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(messageRowMapper.mapRow(resultSet)).thenReturn(new OutboundMessage<>());
    }

    @SneakyThrows
    void mockForExceptionFromExecuteQuery() {
        when(messageReadQueryProvider.getReadQuery()).thenReturn(READ_SQL);
        DataSource dataSource = mock(DataSource.class);
        when(dataSourceResolver.resolve(SOURCE_1)).thenReturn(dataSource);
        Connection connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);
        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.prepareStatement(READ_SQL)).thenReturn(statement);
        doNothing().when(preparedStatementSetter).setValues(messageFilterProperties, statement);
        when(statement.executeQuery()).thenThrow(new SQLException());
    }

    @SneakyThrows
    void mockForExceptionFromRowMapper() {
        when(messageReadQueryProvider.getReadQuery()).thenReturn(READ_SQL);
        DataSource dataSource = mock(DataSource.class);
        when(dataSourceResolver.resolve(SOURCE_1)).thenReturn(dataSource);
        Connection connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);
        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.prepareStatement(READ_SQL)).thenReturn(statement);
        doThrow(new SQLException()).when(preparedStatementSetter).setValues(any(), any());
    }

    @SneakyThrows
    void mockForExceptionFromStatement() {
        when(messageReadQueryProvider.getReadQuery()).thenReturn(READ_SQL);
        DataSource dataSource = mock(DataSource.class);
        when(dataSourceResolver.resolve(SOURCE_1)).thenReturn(dataSource);
        Connection connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(READ_SQL)).thenThrow(new SQLException());
    }

    @SneakyThrows
    void mockForExceptionFromConnection() {
        when(messageReadQueryProvider.getReadQuery()).thenReturn(READ_SQL);
        DataSource dataSource = mock(DataSource.class);
        when(dataSourceResolver.resolve(SOURCE_1)).thenReturn(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
    }

    @BeforeEach
    void setup() {
        dataSourceResolver = mock(DataSourceResolver.class);
        messageReadQueryProvider = mock(MessageReadQueryProvider.class);
        preparedStatementSetter = mock(PreparedStatementSetter.class);
        messageFilterProperties = DefaultMessageFilterProperties.getDefault();
        messageRowMapper = mock(MessageRowMapper.class);
        outboundMessageReader = new OutboundMessageReader<>(dataSourceResolver, messageReadQueryProvider,
            preparedStatementSetter, messageRowMapper);

    }

    @AfterEach
    void clearMocks() {
        Mockito.clearAllCaches();
    }

    @Test
    void shouldReadMessagesWithFilterProperties() {
        mockForValidMessage();
        Collection<OutboundMessage<UUID>> messages = outboundMessageReader.read(MESSAGE_SOURCE_PROPERTIES,
            Optional.of(messageFilterProperties));
        assertAll(
            () -> assertNotNull(messages),
            () -> assertEquals(1, messages.size())
        );
    }

    @Test
    void shouldReadMessagesWithNullFilterProperties() {
        mockForValidMessage();
        Collection<OutboundMessage<UUID>> messages = outboundMessageReader.read(MESSAGE_SOURCE_PROPERTIES);
        assertAll(
            () -> assertNotNull(messages),
            () -> assertEquals(1, messages.size())
        );
    }

    @Test
    void shouldThrowExceptionForNoDataSource() {
        when(dataSourceResolver.resolve(SOURCE_1)).thenReturn(null);
        assertThrows(InvalidDataSourceException.class, () -> outboundMessageReader.read(MESSAGE_SOURCE_PROPERTIES));
    }


    @Test
    void shouldThrowExceptionForInvalidMessageSourceProperties() {
        assertAll(
            () -> assertThrows(InvalidMessageSourceProperties.class, () -> outboundMessageReader.read(null)),
            () -> assertThrows(InvalidMessageSourceProperties.class,
                () -> outboundMessageReader.read(MessageSourceProperties.builder().build())),
            () -> assertThrows(InvalidMessageSourceProperties.class,
                () -> outboundMessageReader.read(MessageSourceProperties.builder().identifier(null).build())),
            () -> assertThrows(InvalidMessageSourceProperties.class,
                () -> outboundMessageReader.read(MessageSourceProperties.builder().identifier("").build()))
        );
    }

    @Test
    void shouldThrowExceptionForNullSql() {
        when(dataSourceResolver.resolve(SOURCE_1)).thenReturn(mock(DataSource.class));
        when(messageReadQueryProvider.getReadQuery()).thenReturn(null);
        assertThrows(InvalidSqlException.class,
            () -> outboundMessageReader.read(MESSAGE_SOURCE_PROPERTIES));
    }

    @Test
    void shouldThrowExceptionForBlankSql() {
        when(dataSourceResolver.resolve(SOURCE_1)).thenReturn(mock(DataSource.class));
        when(messageReadQueryProvider.getReadQuery()).thenReturn("");
        assertThrows(InvalidSqlException.class,
            () -> outboundMessageReader.read(MESSAGE_SOURCE_PROPERTIES));
    }

    @Test
    void shouldThrowExceptionForExecuteQuery() {
        mockForExceptionFromExecuteQuery();
        assertThrows(DataAccessException.class,
            () -> {
                try {
                    outboundMessageReader.read(MESSAGE_SOURCE_PROPERTIES);
                } catch (Exception e) {
                    log.error(
                        e.getMessage(), e
                    );
                    throw e;
                }
            });
    }

    @Test
    void shouldThrowExceptionForExecuteRowMapper() {
        mockForExceptionFromRowMapper();
        assertThrows(DataAccessException.class,
            () -> outboundMessageReader.read(MESSAGE_SOURCE_PROPERTIES));
    }

    @Test
    void shouldThrowExceptionForStatementError() {
        mockForExceptionFromStatement();
        assertThrows(DataAccessException.class,
            () -> outboundMessageReader.read(MESSAGE_SOURCE_PROPERTIES));
    }

    @Test
    void shouldThrowExceptionForCreateConnection() {
        mockForExceptionFromConnection();
        assertThrows(DataAccessException.class,
            () -> outboundMessageReader.read(MESSAGE_SOURCE_PROPERTIES));
    }

}