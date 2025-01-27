package com.consolefire.relayer.outbox.writer;

import com.consolefire.relayer.model.source.MessageSourceProperties;
import com.consolefire.relayer.model.validation.InvalidMessageException;
import com.consolefire.relayer.model.validation.MessageValidator;
import com.consolefire.relayer.model.validation.ValidationResult;
import com.consolefire.relayer.outbox.model.OutboundMessage;
import com.consolefire.relayer.util.data.DataSourceResolver;
import com.consolefire.relayer.writer.MessageInsertStatementSetter;
import com.consolefire.relayer.writer.MessageWriteQueryProvider;
import com.consolefire.relayer.writer.MessageWriter;
import com.consolefire.relayer.writer.MessageWriterErrorException;
import com.consolefire.relayer.writer.MessageWriterTransactionSupport;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class BaseOutboundMessageWriter<ID extends Serializable>
    implements MessageWriter<ID, OutboundMessage<ID>> {

    private final DataSourceResolver dataSourceResolver;
    private final MessageValidator<ID, OutboundMessage<ID>> messageValidator;
    private final MessageWriteQueryProvider<ID, OutboundMessage<ID>> messageWriteQueryProvider;
    private final MessageInsertStatementSetter<ID, OutboundMessage<ID>> messageInsertStatementSetter;
    private final MessageWriterTransactionSupport messageWriterTransactionSupport;

    @Override
    public <S extends OutboundMessage<ID>> S write(
        MessageSourceProperties messageSourceProperties,
        OutboundMessage<ID> message) {
        ValidationResult validationResult = messageValidator.validate(message);
        if (!validationResult.isValid()) {
            throw new InvalidMessageException(message, validationResult);
        }
        DataSource dataSource = dataSourceResolver.resolve(messageSourceProperties.getIdentifier());
        if (null == dataSource) {
            throw new RuntimeException("DataSource is null");
        }
        try (Connection connection = dataSource.getConnection()) {
            String insertQuery = messageWriteQueryProvider.getInsertQuery(message);
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                messageInsertStatementSetter.setProperties(statement, message);
                int count = messageWriterTransactionSupport.usingTransaction(connection, statement);
                if (count != 1) {
                    throw new MessageWriterErrorException();
                }
            }
            return (S) message;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
