package com.consolefire.relayer.core.reader;

import com.consolefire.relayer.core.data.MessageRowMapper;
import com.consolefire.relayer.core.data.PreparedStatementSetter;
import com.consolefire.relayer.core.data.query.SelectQuery;
import com.consolefire.relayer.model.OutboundMessage;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.Map;

public class OutboundMessageSource<ID extends Serializable>
        extends MessageSource<ID, OutboundMessage<ID>> {


    public OutboundMessageSource(
            String identifier,
            DataSource dataSource,
            SelectQuery selectQuery,
            PreparedStatementSetter<Map<String, Object>> preparedStatementSetter,
            Map<String, Object> selectParameters,
            MessageRowMapper<ID, OutboundMessage<ID>> messageRowMapper) {
        super(identifier, dataSource, selectQuery, preparedStatementSetter, selectParameters, messageRowMapper);
    }
}
