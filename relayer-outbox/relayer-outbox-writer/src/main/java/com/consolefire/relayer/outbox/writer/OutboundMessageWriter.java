package com.consolefire.relayer.outbox.writer;

import com.consolefire.relayer.model.validation.MessageValidator;
import com.consolefire.relayer.outbox.model.OutboundMessage;
import com.consolefire.relayer.util.data.DataSourceResolver;
import com.consolefire.relayer.writer.AbstractMessageWriter;
import com.consolefire.relayer.writer.MessageInsertStatementSetter;
import com.consolefire.relayer.writer.MessageWriteQueryProvider;
import com.consolefire.relayer.writer.MessageWriterErrorException;
import com.consolefire.relayer.writer.MessageWriterTransactionSupport;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OutboundMessageWriter<ID extends Serializable>
    extends AbstractMessageWriter<ID, OutboundMessage<ID>> {

    private final MessageWriterTransactionSupport messageWriterTransactionSupport;

    public OutboundMessageWriter(DataSourceResolver dataSourceResolver,
        MessageValidator<ID, OutboundMessage<ID>> messageValidator,
        MessageWriteQueryProvider messageWriteQueryProvider,
        MessageInsertStatementSetter<ID, OutboundMessage<ID>> messageInsertStatementSetter,
        MessageWriterTransactionSupport messageWriterTransactionSupport) {
        super(dataSourceResolver, messageValidator, messageWriteQueryProvider, messageInsertStatementSetter);
        this.messageWriterTransactionSupport = messageWriterTransactionSupport;
    }

    @Override
    protected long executeInsert(Connection connection, PreparedStatement statement) throws SQLException {
        log.info("Executing insert in transaction");
        int count = messageWriterTransactionSupport.usingTransaction(connection, statement);
        if (count != 1) {
            throw new MessageWriterErrorException();
        }
        return count;
    }
}
