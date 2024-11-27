package ct.com.consolefire.relayer.writer.support;

import com.consolefire.relayer.core.data.query.InsertQuery;
import com.consolefire.relayer.writer.jdbc.OutboundMessageInsertPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public class TestPreparedStatementSetter
        extends OutboundMessageInsertPreparedStatementSetter<UUID> {

    public TestPreparedStatementSetter(InsertQuery insertQuery, Map<String, String> fieldColumnMap) {
        super(insertQuery, fieldColumnMap);
    }

    @Override
    protected void setMessageId(UUID messageId, PreparedStatement preparedStatement, int index) throws SQLException {
        preparedStatement.setString(index, messageId.toString());
    }
}
