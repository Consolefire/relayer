package com.consolefire.relayer.outbox.core.data.repository;

import com.consolefire.relayer.core.data.MessageRowMapper;
import com.consolefire.relayer.util.data.PreparedStatementSetter;
import com.consolefire.relayer.util.query.Parameter;
import com.consolefire.relayer.util.query.SelectQuery;
import com.consolefire.relayer.core.data.query.MessageSelectQueryExecutor;
import com.consolefire.relayer.outbox.model.OutboundMessage;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class OutboundMessageSelectQueryExecutor<ID extends Serializable>
    implements MessageSelectQueryExecutor<ID, OutboundMessage<ID>, SelectQuery> {

    private final MessageRowMapper<ID, OutboundMessage<ID>> outboundMessageRowMapper;

    @Override
    public Collection<OutboundMessage<ID>> execute(DataSource dataSource, SelectQuery query,
        List<Parameter<?>> parameters) {
        String sql = query.toStatement();
        PreparedStatementSetter<List<Parameter<?>>> pSetter = (source, statement) -> {

        };
        Collection<OutboundMessage<ID>> outboundMessages = new ArrayList<>();
        try (Connection connection = dataSource.getConnection(); //
            PreparedStatement statement = connection.prepareStatement(sql)) {
            pSetter.setValues(parameters, statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    OutboundMessage<ID> message = outboundMessageRowMapper.mapRow(resultSet);
                    outboundMessages.add(message);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return outboundMessages;
    }
}
