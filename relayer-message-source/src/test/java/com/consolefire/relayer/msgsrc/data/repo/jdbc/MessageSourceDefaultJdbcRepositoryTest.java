package com.consolefire.relayer.msgsrc.data.repo.jdbc;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.consolefire.relayer.msgsrc.data.MessageSourceConfigurationJsonConverter;
import com.consolefire.relayer.msgsrc.data.entity.MessageSource;
import com.consolefire.relayer.msgsrc.data.query.DefaultMessageSourceColumnDefinitionProvider;
import com.consolefire.relayer.msgsrc.data.query.DefaultMessageSourceParameterDefinitionProvider;
import com.consolefire.relayer.msgsrc.data.query.DefaultMessageSourceQueryProvider;
import com.consolefire.relayer.msgsrc.data.query.MessageSourceColumnDefinitionProvider;
import com.consolefire.relayer.msgsrc.data.query.MessageSourcePreparedStatementSetters;
import com.consolefire.relayer.msgsrc.data.query.MessageSourceQueryProvider;
import com.consolefire.relayer.testutils.data.TestDataSourceProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Disabled
@Testcontainers
class MessageSourceDefaultJdbcRepositoryIntegrationTest {

    @Container
    private static final GenericContainer<?> h2 = new GenericContainer<>(DockerImageName.parse("oscarfonts/h2"))
        .withExposedPorts(1521);

    private static final String CFG_KEY_DATA_SOURCE = "DATA_SOURCE";
    private static final TestDataSourceProperties DATA_SOURCE_PROPERTIES
        = TestDataSourceProperties.builder()
        .url("jdbc:somedb//host:1234/dba")
        .username("user1")
        .password("pass1")
        .parameters(Map.of("A", "one", "B", "2"))
        .schemaName("sch01")
        .build();

    private DataSource dataSource;
    private ObjectMapper objectMapper;

    private MessageSourceQueryProvider messageSourceQueryProvider;

    private MessageSourceDefaultJdbcRepository repository;
    private MessageSourcePreparedStatementSetters messageSourcePreparedStatementSetterProvider;
    private MessageSourceConfigurationJsonConverter configurationJsonConverter;
    private MessageSourceColumnDefinitionProvider columnDefinitionProvider;

    @BeforeEach
    void setUp() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        basicDataSource.setUsername("sa");
        basicDataSource.setPassword("");
        basicDataSource.setDriverClassName("org.h2.Driver");
        this.dataSource = basicDataSource;
        this.objectMapper = new ObjectMapper();

        messageSourceQueryProvider = new DefaultMessageSourceQueryProvider();

        messageSourcePreparedStatementSetterProvider
            = new MessageSourcePreparedStatementSetters(new DefaultMessageSourceParameterDefinitionProvider(),
            objectMapper);
        configurationJsonConverter = new MessageSourceConfigurationJsonConverter(
            objectMapper);
        columnDefinitionProvider = new DefaultMessageSourceColumnDefinitionProvider();
        this.repository = new MessageSourceDefaultJdbcRepository(
            dataSource, new MessageSourceRowMapper(columnDefinitionProvider, configurationJsonConverter),
            messageSourceQueryProvider,
            messageSourcePreparedStatementSetterProvider);

        createTable();
    }


    @AfterEach
    void tearDown() {
        dropTable();
    }

    private void createTable() {
        try (var connection = dataSource.getConnection();
            var statement = connection.createStatement()) {
            statement.execute("CREATE TABLE message_source (" +
                "identifier VARCHAR(255) PRIMARY KEY, " +
                "configuration TEXT, " +
                "state VARCHAR(50), " +
                "created_at TIMESTAMP, " +
                "updated_at TIMESTAMP)");
        } catch (Exception e) {
            throw new RuntimeException("Failed to create table", e);
        }
    }

    private void dropTable() {
        try (var connection = dataSource.getConnection();
            var statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS message_source");
        } catch (Exception e) {
            throw new RuntimeException("Failed to drop table", e);
        }
    }


    @SneakyThrows
    private MessageSource createMessageSource(String id) {
        ObjectNode config = new ObjectNode(JsonNodeFactory.instance);
        config.putPOJO(CFG_KEY_DATA_SOURCE, DATA_SOURCE_PROPERTIES);

        return MessageSource.builder()
            .identifier(id)
            .state(MessageSource.State.ACTIVE)
            .configuration(config)
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();
    }

    private void insertMessageSource(MessageSource messageSource) {
        try (var connection = dataSource.getConnection();
            var statement = connection.prepareStatement(
                "INSERT INTO message_source (identifier, configuration, state, created_at, updated_at) VALUES (?, ?, ?, ?, ?)")) {
            statement.setString(1, messageSource.getIdentifier());
            String configJson = messageSource.getConfiguration().toPrettyString();
            statement.setString(2, configJson);
            statement.setString(3, messageSource.getState().toString());
            statement.setObject(4, Instant.now());
            statement.setObject(5, Instant.now());
            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Failed to insert message source", e);
        }
    }

    public void assertConfiguration(JsonNode configNode) {
        assertThatJson(configNode)
            .node(CFG_KEY_DATA_SOURCE)
            .isNotNull();
        JsonNode dataSourceJson = configNode.get(CFG_KEY_DATA_SOURCE);
        assertThatJson(dataSourceJson)
            .node("url").isEqualTo(DATA_SOURCE_PROPERTIES.getUrl());

        assertThatJson(dataSourceJson)
            .node("username").isEqualTo(DATA_SOURCE_PROPERTIES.getUsername());

        assertThatJson(dataSourceJson)
            .node("password").isEqualTo(DATA_SOURCE_PROPERTIES.getPassword());

        assertThatJson(dataSourceJson)
            .node("schemaName").isEqualTo(DATA_SOURCE_PROPERTIES.getSchemaName());

        assertThatJson(dataSourceJson)
            .node("parameters.A").asString().isEqualTo(DATA_SOURCE_PROPERTIES.getParameters().get("A"));

        assertThatJson(dataSourceJson)
            .node("parameters.B").asString().isEqualTo(DATA_SOURCE_PROPERTIES.getParameters().get("B"));

        assertThatJson(dataSourceJson)
            .node("testConnectionOnStart")
            .isEqualTo(DATA_SOURCE_PROPERTIES.isTestConnectionOnStart());

        assertThatJson(dataSourceJson)
            .node("testSql").isEqualTo(DATA_SOURCE_PROPERTIES.getTestSql());
    }

    @Test
    void findById_shouldReturnMessageSource_whenFound() {
        MessageSource messageSource = createMessageSource("testId");
        insertMessageSource(messageSource);

        MessageSource result = repository.findById("testId");

        assertNotNull(result);
        assertEquals(messageSource.getIdentifier(), result.getIdentifier());
        assertConfiguration(result.getConfiguration());
        assertEquals(messageSource.getState(), result.getState());
    }

    @Test
    void findById_shouldReturnNull_whenNotFound() {
        MessageSource result = repository.findById("nonExistentId");
        assertNull(result);
    }

    @Test
    void saveOrUpdate_shouldSaveNewMessageSource_whenDoesNotExist() {
        MessageSource messageSource = createMessageSource("newId");

        int result = repository.save(messageSource);
        assertEquals(1, result);

        MessageSource saved = repository.findById("newId");
        assertNotNull(saved);
        assertEquals(messageSource.getIdentifier(), saved.getIdentifier());
        assertConfiguration(saved.getConfiguration());
        assertEquals(messageSource.getState(), saved.getState());
    }

    @Test
    void saveExistingMessageSource_whenExists() {
        MessageSource messageSource = createMessageSource("existingId");
        insertMessageSource(messageSource);

        MessageSource updatedMessageSource = messageSource.withState(MessageSource.State.INACTIVE);
        int result = repository.save(updatedMessageSource);
        assertEquals(1, result);

        MessageSource updated = repository.findById("existingId");
        assertNotNull(updated);
        assertEquals(updatedMessageSource.getIdentifier(), updated.getIdentifier());
        assertThatJson(updated.getConfiguration()).isEqualTo(updatedMessageSource.getConfiguration());
        assertEquals(MessageSource.State.INACTIVE, updated.getState());
    }

    @Test
    void updateState_shouldUpdateState_whenCalled() {
        MessageSource messageSource = createMessageSource("stateId");
        insertMessageSource(messageSource);

        int result = repository.updateState("stateId", MessageSource.State.INACTIVE);
        assertEquals(1, result);

        MessageSource updated = repository.findById("stateId");
        assertEquals(MessageSource.State.INACTIVE, updated.getState());
    }

    @Test
    void updateConfiguration_shouldUpdateConfiguration_whenCalled() {
        MessageSource messageSource = createMessageSource("configId");
        insertMessageSource(messageSource);

        ObjectNode updatedConfig = new ObjectNode(JsonNodeFactory.instance);
        updatedConfig.put("updatedKey", "newValue");
        int result = repository.updateConfiguration("configId", updatedConfig);
        assertEquals(1, result);

        MessageSource updated = repository.findById("configId");
        assertThatJson(updated.getConfiguration()).isEqualTo(updatedConfig);
    }

    @Test
    void findAll_shouldReturnListOfMessageSources_whenFound() {
        MessageSource messageSource1 = createMessageSource("id1");
        MessageSource messageSource2 = createMessageSource("id2").withState(MessageSource.State.INACTIVE);
        insertMessageSource(messageSource1);
        insertMessageSource(messageSource2);

        List<MessageSource> results = repository.findAll();
        assertEquals(2, results.size());
    }

}
