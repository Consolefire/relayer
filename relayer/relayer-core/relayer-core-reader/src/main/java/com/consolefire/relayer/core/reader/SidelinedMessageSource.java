package com.consolefire.relayer.core.reader;

import com.consolefire.relayer.core.data.MessageRowMapper;
import com.consolefire.relayer.core.data.PreparedStatementSetter;
import com.consolefire.relayer.core.data.query.SelectQuery;
import com.consolefire.relayer.model.SidelinedMessage;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.Map;

public class SidelinedMessageSource<ID extends Serializable>
        extends MessageSource<ID, SidelinedMessage<ID>> {


    public SidelinedMessageSource(
            String identifier,
            DataSource dataSource,
            SelectQuery selectQuery,
            PreparedStatementSetter<Map<String, Object>> preparedStatementSetter,
            Map<String, Object> selectParameters,
            MessageRowMapper<ID, SidelinedMessage<ID>> messageRowMapper) {
        super(identifier, dataSource, selectQuery, preparedStatementSetter, selectParameters, messageRowMapper);
    }
}
