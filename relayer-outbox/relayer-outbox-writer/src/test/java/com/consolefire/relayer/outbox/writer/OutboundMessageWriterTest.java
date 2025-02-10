package com.consolefire.relayer.outbox.writer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.consolefire.relayer.model.source.MessageSourceProperties;
import com.consolefire.relayer.model.validation.InvalidMessageException;
import com.consolefire.relayer.model.validation.MessageValidator;
import com.consolefire.relayer.outbox.model.OutboundMessage;
import com.consolefire.relayer.outbox.writer.validation.DefaultOutboundMessageValidator;
import com.consolefire.relayer.util.data.DataSourceResolver;
import com.consolefire.relayer.util.validation.ValidationResult;
import com.consolefire.relayer.writer.MessageInsertStatementSetter;
import com.consolefire.relayer.writer.MessageWriteQueryProvider;
import com.consolefire.relayer.writer.MessageWriter;
import com.consolefire.relayer.writer.MessageWriterErrorException;
import com.consolefire.relayer.writer.MessageWriterTransactionSupport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OutboundMessageWriterTest {

    private static final String MESSAGE_SOURCE_ID = UUID.randomUUID().toString();
    private static final String MESSAGE_INSETR_SQL = "";


    private DataSourceResolver dataSourceResolver;
    private MessageValidator<UUID, OutboundMessage<UUID>> messageValidator;
    private MessageWriteQueryProvider messageWriteQueryProvider;
    private MessageInsertStatementSetter<UUID, OutboundMessage<UUID>> messageInsertStatementSetter;
    private MessageWriterTransactionSupport messageWriterTransactionSupport;
    private MessageWriter<UUID, OutboundMessage<UUID>> outboundMessageWriter;

    @BeforeEach
    void setup() {

        messageValidator = mock(DefaultOutboundMessageValidator.class);
        dataSourceResolver = mock(DataSourceResolver.class);
        messageWriteQueryProvider = mock(MessageWriteQueryProvider.class);
        messageInsertStatementSetter = mock(MessageInsertStatementSetter.class);
        messageWriterTransactionSupport = mock(MessageWriterTransactionSupport.class);

        outboundMessageWriter = new OutboundMessageWriter<>(dataSourceResolver, messageValidator,
            messageWriteQueryProvider, messageInsertStatementSetter, messageWriterTransactionSupport);
    }

    @SneakyThrows
    private void mockValidInsertCase(OutboundMessage<UUID> message) {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        when(dataSourceResolver.resolve(MESSAGE_SOURCE_ID)).thenReturn(dataSource);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(MESSAGE_INSETR_SQL)).thenReturn(statement);
        doNothing().when(messageInsertStatementSetter).setProperties(statement, message);
    }

    @SneakyThrows
    private void mockInvalidConnection() {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);

        when(dataSourceResolver.resolve(MESSAGE_SOURCE_ID)).thenReturn(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
    }

    @SneakyThrows
    private void mockInvalidStatement() {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);

        when(dataSourceResolver.resolve(MESSAGE_SOURCE_ID)).thenReturn(dataSource);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(MESSAGE_INSETR_SQL)).thenThrow(new SQLException());
    }

    @Test
    @SneakyThrows
    void shouldWriteMessage() {
        OutboundMessage<UUID> message = new OutboundMessage<>();
        message.setMessageId(UUID.randomUUID());
        message.setMessageSequence(1001L);
        message.setChannelName("test-channel");
        message.setGroupId(UUID.randomUUID().toString());
        ValidationResult validationResult = ValidationResult.builder(messageValidator, message).withTest(() -> true)
            .build();
        when(messageValidator.validate(message)).thenReturn(validationResult);
        mockValidInsertCase(message);
        when(messageWriterTransactionSupport.usingTransaction(any(), any())).thenReturn(1);

        MessageSourceProperties messageSourceProperties
            = MessageSourceProperties.builder()
            .identifier(MESSAGE_SOURCE_ID)
            .build();

        long count = outboundMessageWriter.insert(messageSourceProperties, message);
        assertEquals(1, count);
    }


    @Test
    void shouldThrowExceptionWhenMessageNotInserted() throws SQLException {
        OutboundMessage<UUID> message = new OutboundMessage<>();
        message.setMessageId(UUID.randomUUID());
        message.setMessageSequence(1001L);
        message.setChannelName("test-channel");
        message.setGroupId(UUID.randomUUID().toString());
        ValidationResult validationResult = ValidationResult.builder(messageValidator, message).withTest(() -> true)
            .build();
        when(messageValidator.validate(message)).thenReturn(validationResult);
        mockValidInsertCase(message);
        when(messageWriterTransactionSupport.usingTransaction(any(), any())).thenReturn(0);

        MessageSourceProperties messageSourceProperties
            = MessageSourceProperties.builder()
            .identifier(MESSAGE_SOURCE_ID)
            .build();
        assertThrows(MessageWriterErrorException.class,
            () -> outboundMessageWriter.insert(messageSourceProperties, message));
    }

    @Test
    void shouldThrowExceptionWhenDataSourceIsNull() throws SQLException {
        OutboundMessage<UUID> message = new OutboundMessage<>();
        message.setMessageId(UUID.randomUUID());
        message.setMessageSequence(1001L);
        message.setChannelName("test-channel");
        message.setGroupId(UUID.randomUUID().toString());
        ValidationResult validationResult = ValidationResult.builder(messageValidator, message).withTest(() -> true)
            .build();
        when(messageValidator.validate(message)).thenReturn(validationResult);
        when(dataSourceResolver.resolve(MESSAGE_SOURCE_ID)).thenReturn(null);
        MessageSourceProperties messageSourceProperties
            = MessageSourceProperties.builder()
            .identifier(MESSAGE_SOURCE_ID)
            .build();
        assertThrows(MessageWriterErrorException.class,
            () -> outboundMessageWriter.insert(messageSourceProperties, message));
    }

    @Test
    void shouldThrowExceptionForSQLStatementError() {
        OutboundMessage<UUID> message = new OutboundMessage<>();
        message.setMessageId(UUID.randomUUID());
        message.setMessageSequence(1001L);
        message.setChannelName("test-channel");
        message.setGroupId(UUID.randomUUID().toString());
        ValidationResult validationResult = ValidationResult.builder(messageValidator, message).withTest(() -> true)
            .build();
        when(messageValidator.validate(message)).thenReturn(validationResult);
        mockInvalidStatement();
        MessageSourceProperties messageSourceProperties
            = MessageSourceProperties.builder()
            .identifier(MESSAGE_SOURCE_ID)
            .build();
        assertThrows(MessageWriterErrorException.class,
            () -> outboundMessageWriter.insert(messageSourceProperties, message));
    }

    @Test
    void shouldThrowExceptionForSQLConnectionError() {
        OutboundMessage<UUID> message = new OutboundMessage<>();
        message.setMessageId(UUID.randomUUID());
        message.setMessageSequence(1001L);
        message.setChannelName("test-channel");
        message.setGroupId(UUID.randomUUID().toString());
        ValidationResult validationResult = ValidationResult.builder(messageValidator, message).withTest(() -> true)
            .build();
        when(messageValidator.validate(message)).thenReturn(validationResult);
        mockInvalidConnection();
        MessageSourceProperties messageSourceProperties
            = MessageSourceProperties.builder()
            .identifier(MESSAGE_SOURCE_ID)
            .build();
        assertThrows(MessageWriterErrorException.class,
            () -> outboundMessageWriter.insert(messageSourceProperties, message));
    }


    @Test
    void shouldThrowExceptionWhenMessageIsInvalid() {
        OutboundMessage<UUID> message = new OutboundMessage<>();
        message.setMessageId(UUID.randomUUID());
        message.setMessageSequence(1001L);
        message.setChannelName("test-channel");
        message.setGroupId(UUID.randomUUID().toString());
        ValidationResult validationResult = ValidationResult.builder(messageValidator, message).withTest(() -> false)
            .build();
        when(messageValidator.validate(message)).thenReturn(validationResult);

        MessageSourceProperties messageSourceProperties
            = MessageSourceProperties.builder()
            .identifier(MESSAGE_SOURCE_ID)
            .build();
        assertThrows(InvalidMessageException.class,
            () -> outboundMessageWriter.insert(messageSourceProperties, message));
    }

    @Test
    void shouldThrowExceptionWhenMessageValidationResultIsNull() {
        OutboundMessage<UUID> message = new OutboundMessage<>();
        message.setMessageId(UUID.randomUUID());
        message.setMessageSequence(1001L);
        message.setChannelName("test-channel");
        message.setGroupId(UUID.randomUUID().toString());
        when(messageValidator.validate(message)).thenReturn(null);

        MessageSourceProperties messageSourceProperties
            = MessageSourceProperties.builder()
            .identifier(MESSAGE_SOURCE_ID)
            .build();
        assertThrows(MessageWriterErrorException.class,
            () -> outboundMessageWriter.insert(messageSourceProperties, message));
    }
}