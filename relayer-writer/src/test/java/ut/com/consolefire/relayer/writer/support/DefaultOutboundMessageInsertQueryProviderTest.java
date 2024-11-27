package ut.com.consolefire.relayer.writer.support;

import com.consolefire.relayer.writer.support.DefaultOutboundMessageInsertQueryProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static com.consolefire.relayer.testutils.data.TestMessageStoreMetadata.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class DefaultOutboundMessageInsertQueryProviderTest {


    private static final String NO_PARAM_INSERT_SQL_WITH_SCHEMA = "insert into " + SCHEMA_MESSAGE_STORE + "." + TABLE_OUTBOUND_MESSAGE + " ("
            + "message_id, group_id, channel_name, payload, headers, metadata, state, relayed_at, relay_count, created_at, updated_at"
            + ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String NO_PARAM_INSERT_SQL_WITHOUT_SCHEMA = "insert into " + TABLE_OUTBOUND_MESSAGE + " ("
            + "message_id, group_id, channel_name, payload, headers, metadata, state, relayed_at, relay_count, created_at, updated_at"
            + ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static void logAndAssertGeneratedStatement(String expectedStatement, String statement) {
        log.info("Expected Statement: {}", expectedStatement);
        log.info("Generated Statement: {}", statement);
        assertNotNull(statement);
        assertEquals(expectedStatement, statement);
    }

    @Test
    void shouldCreateQueryProviderWithSchemaAndDefaultsAndNoNamedParams() {
        final String expectedStatement = NO_PARAM_INSERT_SQL_WITH_SCHEMA;
        DefaultOutboundMessageInsertQueryProvider queryProvider = new DefaultOutboundMessageInsertQueryProvider(SCHEMA_MESSAGE_STORE, TABLE_OUTBOUND_MESSAGE, OUTBOUND_MESSAGE_COLUMN_MAPPINGS);
        String statement = queryProvider.getQuery().toStatement();
        logAndAssertGeneratedStatement(expectedStatement, statement);
    }


    @Test
    void shouldCreateQueryProviderWithoutSchemaAndWithDefaultsAndNoNamedParams() {
        final String expectedStatement = NO_PARAM_INSERT_SQL_WITHOUT_SCHEMA;
        DefaultOutboundMessageInsertQueryProvider queryProvider = new DefaultOutboundMessageInsertQueryProvider(null, TABLE_OUTBOUND_MESSAGE, OUTBOUND_MESSAGE_COLUMN_MAPPINGS);
        String statement = queryProvider.getQuery().toStatement();
        logAndAssertGeneratedStatement(expectedStatement, statement);
    }

}
