package com.consolefire.relayer.outbox.core.data;

import com.consolefire.relayer.core.data.MessageRowMapper;
import com.consolefire.relayer.outbox.model.OutboundMessage;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractOutboundMessageRowMapper<ID extends Serializable>
    implements MessageRowMapper<ID, OutboundMessage<ID>> {

    /*
    om.message_id,
            om.message_sequence,
            om.group_id,
            om.channel_name,
            om.payload,
            om.headers,
            om.metadata,
            om.state,
            om.attempted_at,
            om.attempt_count,
            om.created_at,
            om.updated_at
     */

    @Override
    public OutboundMessage<ID> mapRow(ResultSet resultSet) throws SQLException {
        OutboundMessage<ID> message = OutboundMessage.<ID>builder()
            .messageId(mapMessageId(resultSet))
            .messageSequence(resultSet.getLong("message_sequence"))
            .build();
        return message;
    }

    protected abstract ID mapMessageId(ResultSet resultSet);
}
