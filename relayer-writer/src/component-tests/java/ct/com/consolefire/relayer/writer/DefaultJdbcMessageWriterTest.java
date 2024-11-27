package ct.com.consolefire.relayer.writer;

import static com.consolefire.relayer.testutils.data.TestMessageStoreMetadata.CN_CHANNEL_NAME;
import static com.consolefire.relayer.testutils.data.TestMessageStoreMetadata.CN_CREATED_AT;
import static com.consolefire.relayer.testutils.data.TestMessageStoreMetadata.CN_GROUP_ID;
import static com.consolefire.relayer.testutils.data.TestMessageStoreMetadata.CN_HEADERS;
import static com.consolefire.relayer.testutils.data.TestMessageStoreMetadata.CN_MESSAGE_ID;
import static com.consolefire.relayer.testutils.data.TestMessageStoreMetadata.CN_METADATA;
import static com.consolefire.relayer.testutils.data.TestMessageStoreMetadata.CN_PAYLOAD;
import static com.consolefire.relayer.testutils.data.TestMessageStoreMetadata.CN_RELAYED_AT;
import static com.consolefire.relayer.testutils.data.TestMessageStoreMetadata.CN_RELAY_COUNT;
import static com.consolefire.relayer.testutils.data.TestMessageStoreMetadata.CN_STATE;
import static com.consolefire.relayer.testutils.data.TestMessageStoreMetadata.CN_UPDATED_AT;
import static com.consolefire.relayer.testutils.data.TestMessageStoreMetadata.OUTBOUND_MESSAGE_COLUMN_MAPPINGS;
import static com.consolefire.relayer.testutils.data.TestMessageStoreMetadata.SCHEMA_MESSAGE_STORE;
import static com.consolefire.relayer.testutils.data.TestMessageStoreMetadata.TABLE_OUTBOUND_MESSAGE;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.consolefire.relayer.model.MessageState;
import com.consolefire.relayer.model.outbox.OutboundMessage;
import com.consolefire.relayer.model.outbox.OutboundMessageBuilder;
import com.consolefire.relayer.testutils.data.H2InMemoryDataSourceBuilder;
import com.consolefire.relayer.testutils.data.TestDataSource;
import com.consolefire.relayer.testutils.ext.DataSourceAwareExtension;
import com.consolefire.relayer.writer.support.DefaultOutboundMessageInsertQueryProvider;
import com.radcortez.flyway.test.annotation.FlywayTest;
import ct.com.consolefire.relayer.writer.support.TestPreparedStatementSetter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@DataSourceAwareExtension
@FlywayTest(value = @com.radcortez.flyway.test.annotation.DataSource(H2InMemoryDataSourceBuilder.class),
    additionalLocations = {"db/test-migrations"})
public class DefaultJdbcMessageWriterTest {

    @TestDataSource
    private DataSource dataSource;

    private DefaultOutboundMessageInsertQueryProvider queryProvider;
    private TestPreparedStatementSetter preparedStatementSetter;
    private OutboundMessageBuilder<UUID, String, String, String, OutboundMessage<UUID>> messageBuilder;
    private TestOutboundMessageWriter outboundMessageWriter;

    @BeforeEach
    public void setUp() {
        assertNotNull(dataSource);
        queryProvider = new DefaultOutboundMessageInsertQueryProvider(
            SCHEMA_MESSAGE_STORE, TABLE_OUTBOUND_MESSAGE, OUTBOUND_MESSAGE_COLUMN_MAPPINGS);
        preparedStatementSetter = new TestPreparedStatementSetter(queryProvider.getQuery(),
            queryProvider.getFieldColumnMappings());
        messageBuilder = new OutboundMessageBuilder<>();
        outboundMessageWriter = new TestOutboundMessageWriter(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            queryProvider, preparedStatementSetter, dataSource);
    }

    @Test
    void shouldInsertOutboundMessage() throws Exception {
        UUID messageId = UUID.randomUUID();
        OutboundMessage<UUID> expectedMessage = messageBuilder
            .withMessageId(messageId)
            .withMessageSequence(1l)
            .withGroupId("groupId")
            .withChannelName("channelName")
            .withPayload("payload")
            .withHeaders("headers")
            .withMetadata("metadata")
            .withCreatedAt(Instant.now())
            .withUpdatedAt(Instant.now())
            .withState(MessageState.NEW)
            .withRelayCount(0)
            .withRelayedAt(Instant.now())
            .build();
        outboundMessageWriter.write(expectedMessage);
        @Cleanup Connection connection = dataSource.getConnection();
        String select = format("select * from %s.%s", SCHEMA_MESSAGE_STORE, TABLE_OUTBOUND_MESSAGE);
        List<OutboundMessage<UUID>> outboundMessages = new ArrayList<>();
        @Cleanup Statement statement = connection.createStatement();
        @Cleanup ResultSet resultSet = statement.executeQuery(select);
        while (resultSet.next()) {
            OutboundMessage<UUID> messageFromResultSet = OutboundMessage.<UUID>builder()
                .messageId(UUID.fromString(resultSet.getString(CN_MESSAGE_ID)))
                .groupId(resultSet.getString(CN_GROUP_ID))
                .channelName(resultSet.getString(CN_CHANNEL_NAME))
                .payload(resultSet.getString(CN_PAYLOAD))
                .headers(resultSet.getString(CN_HEADERS))
                .metadata(resultSet.getString(CN_METADATA))
                .createdAt(Optional.ofNullable(resultSet.getTimestamp(CN_CREATED_AT)).map(java.sql.Timestamp::toInstant)
                    .orElse(null))
                .updatedAt(Optional.ofNullable(resultSet.getTimestamp(CN_UPDATED_AT)).map(java.sql.Timestamp::toInstant)
                    .orElse(null))
                .state(MessageState.valueOf(resultSet.getString(CN_STATE)))
                .relayCount(resultSet.getInt(CN_RELAY_COUNT))
                .relayedAt(Optional.ofNullable(resultSet.getTimestamp(CN_RELAYED_AT)).map(java.sql.Timestamp::toInstant)
                    .orElse(null))
                .build();
            outboundMessages.add(messageFromResultSet);
        }
        assertFalse(outboundMessages.isEmpty());
        assertEquals(1, outboundMessages.size());
        OutboundMessage<UUID> actualMessage = outboundMessages.get(0);
        assertEquals(expectedMessage.getMessageId(), actualMessage.getMessageId());
    }
}
