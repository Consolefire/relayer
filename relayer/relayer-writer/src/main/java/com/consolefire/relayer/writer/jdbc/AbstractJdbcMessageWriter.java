package com.consolefire.relayer.writer.jdbc;

import com.consolefire.relayer.core.data.PreparedStatementSetter;
import com.consolefire.relayer.core.data.query.InsertQuery;
import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.model.helper.MessageCopier;
import com.consolefire.relayer.model.validation.MessageValidator;
import com.consolefire.relayer.writer.AbstractMessageWriter;
import com.consolefire.relayer.writer.MessageWriter;
import com.consolefire.relayer.writer.interceptor.AfterWriteInterceptor;
import com.consolefire.relayer.writer.interceptor.BeforeWriteInterceptor;
import com.consolefire.relayer.writer.support.MessageInsertQueryProvider;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public abstract class AbstractJdbcMessageWriter<ID extends Serializable, M extends Message<ID>>
        extends AbstractMessageWriter<ID, M> implements MessageWriter<ID, M> {

    protected final MessageInsertQueryProvider messageInsertQueryProvider;
    protected final PreparedStatementSetter<M> preparedStatementSetter;

    public AbstractJdbcMessageWriter(
            Optional<MessageValidator<ID, M>> messageValidator,
            Optional<BeforeWriteInterceptor<ID, M>> beforeWriteInterceptor,
            Optional<AfterWriteInterceptor<ID, M>> afterWriteInterceptor,
            Optional<MessageCopier<ID, M>> messageCopier,
            MessageInsertQueryProvider messageInsertQueryProvider,
            PreparedStatementSetter<M> preparedStatementSetter) {
        super(messageValidator, beforeWriteInterceptor, afterWriteInterceptor, messageCopier);
        this.messageInsertQueryProvider = messageInsertQueryProvider;
        this.preparedStatementSetter = preparedStatementSetter;
    }


    @Override
    protected <S extends M> S executeWrite(M message) throws Exception {
        Connection connection = getConnection();
        InsertQuery query = messageInsertQueryProvider.getQuery();
        PreparedStatement statement = connection.prepareStatement(query.toStatement());
        preparedStatementSetter.setValues(message, statement);
        int count = statement.executeUpdate();
        if (count != 1) {
            throw new RuntimeException("Failed to execute write statement");
        }
        return (S) message;
    }

    protected abstract Connection getConnection() throws SQLException;
}
