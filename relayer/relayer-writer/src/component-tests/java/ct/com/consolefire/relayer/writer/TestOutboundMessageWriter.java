package ct.com.consolefire.relayer.writer;

import com.consolefire.relayer.core.data.PreparedStatementSetter;
import com.consolefire.relayer.core.data.query.InsertQuery;
import com.consolefire.relayer.model.helper.MessageCopier;
import com.consolefire.relayer.model.outbox.OutboundMessage;
import com.consolefire.relayer.model.validation.MessageValidator;
import com.consolefire.relayer.writer.interceptor.AfterWriteInterceptor;
import com.consolefire.relayer.writer.interceptor.BeforeWriteInterceptor;
import com.consolefire.relayer.writer.jdbc.AbstractJdbcMessageWriter;
import com.consolefire.relayer.writer.support.MessageInsertQueryProvider;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class TestOutboundMessageWriter extends AbstractJdbcMessageWriter<UUID, OutboundMessage<UUID>> {

    private final DataSource dataSource;

    public TestOutboundMessageWriter(
        Optional<MessageValidator<UUID, OutboundMessage<UUID>>> uuidOutboundMessageMessageValidator,
        Optional<BeforeWriteInterceptor<UUID, OutboundMessage<UUID>>> uuidOutboundMessageBeforeWriteInterceptor,
        Optional<AfterWriteInterceptor<UUID, OutboundMessage<UUID>>> uuidOutboundMessageAfterWriteInterceptor,
        Optional<MessageCopier<UUID, OutboundMessage<UUID>>> uuidOutboundMessageMessageCopier,
        MessageInsertQueryProvider messageInsertQueryProvider,
        PreparedStatementSetter<OutboundMessage<UUID>> preparedStatementSetter, DataSource dataSource) {
        super(uuidOutboundMessageMessageValidator, uuidOutboundMessageBeforeWriteInterceptor,
            uuidOutboundMessageAfterWriteInterceptor, uuidOutboundMessageMessageCopier, messageInsertQueryProvider,
            preparedStatementSetter);
        this.dataSource = dataSource;
    }

    @Override
    protected <S extends OutboundMessage<UUID>> S executeWrite(OutboundMessage<UUID> message) throws Exception {
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

    @Override
    protected void processException(OutboundMessage<UUID> message, Exception exception) {
        throw new RuntimeException("Failed to process message", exception);
    }

    @Override
    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
