package com.consolefire.relayer.core.reader;

import com.consolefire.relayer.core.data.MessageRowMapper;
import com.consolefire.relayer.core.data.PreparedStatementSetter;
import com.consolefire.relayer.core.data.query.SelectQuery;
import com.consolefire.relayer.model.Message;
import lombok.Getter;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.Map;

@Getter
public abstract class MessageSource<ID extends Serializable, M extends Message<ID>> {

    private final String identifier;
    private final DataSource dataSource;
    private final SelectQuery selectQuery;
    private final PreparedStatementSetter<Map<String, Object>> preparedStatementSetter;
    private final Map<String, Object> selectParameters;
    private final MessageRowMapper<ID, M> messageRowMapper;

    protected MessageSource(
            String identifier,
            DataSource dataSource,
            SelectQuery selectQuery,
            PreparedStatementSetter<Map<String, Object>> preparedStatementSetter,
            Map<String, Object> selectParameters,
            MessageRowMapper<ID, M> messageRowMapper) {
        this.identifier = identifier;
        this.dataSource = dataSource;
        this.selectQuery = selectQuery;
        this.preparedStatementSetter = preparedStatementSetter;
        this.selectParameters = selectParameters;
        this.messageRowMapper = messageRowMapper;
    }
}
