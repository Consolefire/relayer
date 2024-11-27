package ct.com.consolefire.relayer.writer.support;

import com.radcortez.flyway.test.annotation.FlywayTest;
import com.consolefire.relayer.core.data.query.InsertQuery;
import com.consolefire.relayer.model.MessageState;
import com.consolefire.relayer.model.OutboundMessage;
import com.consolefire.relayer.model.helper.OutboundMessageBuilder;
import com.consolefire.relayer.testutils.data.H2InMemoryDataSourceBuilder;
import com.consolefire.relayer.testutils.data.TestDataSource;
import com.consolefire.relayer.testutils.ext.DataSourceAwareExtension;
import com.consolefire.relayer.writer.support.DefaultOutboundMessageInsertQueryProvider;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.UUID;

import static com.consolefire.relayer.testutils.data.TestMessageStoreMetadata.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@DataSourceAwareExtension
@FlywayTest(value = @com.radcortez.flyway.test.annotation.DataSource(H2InMemoryDataSourceBuilder.class),
        additionalLocations = {"db/test-migrations"})
public class PreparedStatementSetterTest {

    @TestDataSource
    private DataSource dataSource;

    private DefaultOutboundMessageInsertQueryProvider queryProvider;
    private TestPreparedStatementSetter preparedStatementSetter;
    private OutboundMessageBuilder<UUID, String, String, String, OutboundMessage<UUID>> messageBuilder;

    //@BeforeAll
    @SneakyThrows
    public void init() {
        assertNotNull(dataSource);
        String sql = """
                create table test_table (
                    message_id varchar(64) not null,
                    message_sequence bigint not null,
                    group_id varchar(120) not null,
                    channel_name varchar(256) not null,
                    payload clob,
                    headers clob,
                    metadata clob,
                    created_at timestamp not null,
                    updated_at timestamp,
                    state varchar(64) not null,
                    relayed_at timestamp not null,
                    relay_count int default 0,
                    primary key (message_id)
                )""";
        @Cleanup Connection connection = dataSource.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int count = preparedStatement.executeUpdate();
        log.info("Inserted " + count + " rows");
    }

    @BeforeEach
    public void setUp() {
        assertNotNull(dataSource);
        queryProvider = new DefaultOutboundMessageInsertQueryProvider(
                SCHEMA_MESSAGE_STORE, TABLE_OUTBOUND_MESSAGE, OUTBOUND_MESSAGE_COLUMN_MAPPINGS);
        preparedStatementSetter = new TestPreparedStatementSetter(queryProvider.getQuery(), queryProvider.getFieldColumnMappings());
        messageBuilder = new OutboundMessageBuilder<>();
    }

    @Test
    @SneakyThrows
    void shouldSetValuesToNamedParamPreparedStatement() {
        UUID messageId = UUID.randomUUID();
        OutboundMessage<UUID> message = messageBuilder
                .withMessageId(messageId)
                .withMessageSequence(1l)
                .withGroupId("groupId")
                .withChannelName("channelName")
                .withPayload("payload")
                .withHeaders("headers")
                .withMetadata("metadata")
                .withCreatedAt(new Date())
                .withUpdatedAt(new Date())
                .withState(MessageState.NEW)
                .withRelayCount(0)
                .withRelayedAt(new Date())
                .build();


        @Cleanup Connection connection = dataSource.getConnection();
        assertNotNull(connection);
        InsertQuery query = queryProvider.getQuery();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query.toStatement());
        assertNotNull(preparedStatement);
        preparedStatementSetter.setValues(message, preparedStatement);


        ParameterMetaData parameterMetaData = preparedStatement.getParameterMetaData();
        assertNotNull(parameterMetaData);
        assertEquals(query.getParameterCount(), parameterMetaData.getParameterCount());
    }
}
