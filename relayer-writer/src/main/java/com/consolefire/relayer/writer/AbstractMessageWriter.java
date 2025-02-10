package com.consolefire.relayer.writer;

import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.model.source.MessageSourceProperties;
import com.consolefire.relayer.model.validation.InvalidMessageException;
import com.consolefire.relayer.model.validation.MessageValidator;
import com.consolefire.relayer.util.data.DataSourceResolver;
import com.consolefire.relayer.util.validation.ValidationResult;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractMessageWriter<ID extends Serializable, M extends Message<ID>>
    implements MessageWriter<ID, M> {

    private final DataSourceResolver dataSourceResolver;
    private final MessageValidator<ID, M> messageValidator;
    private final MessageWriteQueryProvider messageWriteQueryProvider;
    private final MessageInsertStatementSetter<ID, M> messageInsertStatementSetter;

    @Override
    public long insert(MessageSourceProperties messageSourceProperties, M message) {
        log.info("Inserting message with ID: {} to message source with id: {}", message.getMessageId(),
            messageSourceProperties.getIdentifier());
        log.debug("Target message store: {}", messageSourceProperties);
        log.debug("Message to insert: {}", message);
        validateMessage(message);
        // Get datasource
        DataSource dataSource = dataSourceResolver.resolve(messageSourceProperties.getIdentifier());
        if (dataSource == null) {
            throw new MessageWriterErrorException(
                "Undefined datasource for message source id: " + messageSourceProperties.getIdentifier());
        }
        String insertQuery = messageWriteQueryProvider.getInsertQuery();
        log.debug("Using message insert SQL: {}", insertQuery);
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            messageInsertStatementSetter.setProperties(statement, message);
            return executeInsert(connection, statement);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new MessageWriterErrorException("Failed to execute insert message: " + e.getMessage(), e);
        }
    }

    protected void validateMessage(M message) {
        ValidationResult validationResult = messageValidator.validate(message);
        if (null != validationResult) {
            if (!validationResult.isValid()) {
                log.error("Message is invalid");
                throw new InvalidMessageException(message, validationResult);
            }
        } else {
            throw new MessageWriterErrorException("Failed to validate message");
        }
    }

    protected abstract long executeInsert(Connection connection, PreparedStatement statement) throws SQLException;

}
