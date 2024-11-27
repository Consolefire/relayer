package ct.com.consolefire.relayer.core.reader;

import com.consolefire.relayer.core.data.MessageRowMapper;
import com.consolefire.relayer.core.data.PreparedStatementSetter;
import com.consolefire.relayer.core.data.query.SelectQuery;
import com.consolefire.relayer.core.reader.MessageSource;
import com.consolefire.relayer.model.outbox.OutboundMessage;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class TestMessageSourceReader implements
    com.consolefire.relayer.core.reader.MessageSourceReader<UUID, OutboundMessage<UUID>> {


    @Override
    public Collection<OutboundMessage<UUID>> read(MessageSource messageSource) {
        DataSource dataSource = messageSource.getDataSource();

        SelectQuery selectQuery = messageSource.getSelectQuery();

        PreparedStatementSetter<Map<String, Object>> preparedStatementSetter
                = messageSource.getPreparedStatementSetter();

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery.toStatement());
        ) {
            preparedStatementSetter.setValues(messageSource.getSelectParameters(), preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            MessageRowMapper<UUID, OutboundMessage<UUID>> rowMapper = messageSource.getMessageRowMapper();
            Collection<OutboundMessage<UUID>> outboundMessages = new ArrayList<>();
            while (resultSet.next()) {
                outboundMessages.add(rowMapper.mapRow(resultSet, resultSet.getRow()));
            }
            return outboundMessages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
