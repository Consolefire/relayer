package com.consolefire.relayer.outbox.core.reader;

public class DefaultOutboxMessageReadQueryProvider extends AbstractOutboxMessageReadQueryProvider {

    private static final String SELECT_WITH_PATTERN = """
        SELECT
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
            om.relayed_at,
            om.relay_count,
            om.created_at,
            om.updated_at
        FROM outbound_message om
        LEFT OUTER JOIN sidelined_group sg
            ON (sg.group_id != om.group_id)
        WHERE
            om.state IN ('NEW', 'PARKED', 'RELAY_FAILED')
            AND (om.attempt_count is null or om.attempt_count < ?)
        LIMIT ?""";


    @Override
    protected String buildStatement() {
        return SELECT_WITH_PATTERN;
    }
}
