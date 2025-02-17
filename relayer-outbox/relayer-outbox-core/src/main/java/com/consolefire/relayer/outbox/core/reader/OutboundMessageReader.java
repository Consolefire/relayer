package com.consolefire.relayer.outbox.core.reader;

import com.consolefire.relayer.core.data.MessageReadQueryProvider;
import com.consolefire.relayer.core.data.MessageRowMapper;
import com.consolefire.relayer.core.reader.BaseMessageReader;
import com.consolefire.relayer.core.reader.MessageFilterProperties;
import com.consolefire.relayer.outbox.model.OutboundMessage;
import com.consolefire.relayer.util.data.DataSourceResolver;
import com.consolefire.relayer.util.data.PreparedStatementSetter;
import java.io.Serializable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OutboundMessageReader<ID extends Serializable> extends BaseMessageReader<ID, OutboundMessage<ID>> {

    public OutboundMessageReader(DataSourceResolver dataSourceResolver,
        MessageReadQueryProvider messageReadQueryProvider,
        PreparedStatementSetter<MessageFilterProperties> preparedStatementSetter,
        MessageRowMapper<ID, OutboundMessage<ID>> messageRowMapper) {
        super(dataSourceResolver, messageReadQueryProvider, preparedStatementSetter, messageRowMapper);
    }
}
