package com.consolefire.relayer.sidecar.tmt.data.repository;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.consolefire.relayer.msgsrc.data.entity.MessageSource;
import com.consolefire.relayer.sidecar.tmt.data.repository.TestDataSourceProperties.Fields;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class MessageSourceJdbcRepositoryTest {

    private static final String CFG_KEY_DATA_SOURCE = "DATA_SOURCE";
    private static final TestDataSourceProperties DATA_SOURCE_PROPERTIES
        = TestDataSourceProperties.builder()
        .url("jdbc:somedb//host:1234/dba")
        .password("user1")
        .password("pass1")
        .parameters(Map.of("A", "one", "B", "2"))
        .schemaName("sch01")
        .build();


    private MessageSourceJdbcRepository repository;
    private DataSource dataSource;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");


    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(
            postgres.getJdbcUrl(),
            postgres.getUsername(),
            postgres.getPassword()
        );
        //dataSource.setDriverClassName(postgres.getDriverClassName());

        // Initialize database schema
        initializeDatabase(dataSource);



        repository = new MessageSourceJdbcRepository(dataSource);
    }

    private void initializeDatabase(DataSource dataSource) {
        try (var connection = dataSource.getConnection();
            var statement = connection.createStatement()) {
            statement.execute("drop table if exists message_source");
            statement.execute("CREATE TABLE IF NOT EXISTS message_source (" +
                "identifier VARCHAR(256) PRIMARY KEY, " +
                "state VARCHAR(128), " +
                "configuration TEXT, " +
                "created_at TIMESTAMP NOT NULL DEFAULT NOW(), " +
                "updated_at TIMESTAMP" +
                ")");

        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }


    @Test
    void findById_shouldReturnMessageSource_whenFound() throws JsonProcessingException {
        MessageSource messageSource = createMessageSource("testId");
        repository.saveOrUpdate(messageSource);

        MessageSource found = repository.findById("testId");

        assertNotNull(found);
        assertEquals("testId", found.getIdentifier());
        assertEquals(MessageSource.State.ACTIVE, found.getState());
        assertThatJson(found.getConfiguration())
            .node(CFG_KEY_DATA_SOURCE)
            .isNotNull();
        JsonNode dsCfgNode = found.getConfiguration().get(CFG_KEY_DATA_SOURCE);
        assertThatJson(dsCfgNode)
            .node(Fields.url.name())
            .isPresent()
            .and()
            .isEqualTo(DATA_SOURCE_PROPERTIES.getUrl())
            .isNotNull();
    }

    @Test
    void findById_shouldReturnNull_whenNotFound() {
        MessageSource found = repository.findById("nonExistentId");
        assertNull(found);
    }

    @Test
    void saveOrUpdate_shouldSaveNewMessageSource() throws JsonProcessingException {
        MessageSource messageSource = createMessageSource("newId");
        int result = repository.saveOrUpdate(messageSource);
        assertEquals(1, result);

        MessageSource saved = repository.findById("newId");
        assertNotNull(saved);
        assertEquals("newId", saved.getIdentifier());
    }

    @Test
    void saveOrUpdate_shouldUpdateExistingMessageSource() throws JsonProcessingException {
        MessageSource original = createMessageSource("updateId");
        repository.saveOrUpdate(original);

        MessageSource updated = createMessageSource("updateId");
        updated.setState(MessageSource.State.INACTIVE);
        int result = repository.saveOrUpdate(updated);

        assertEquals(1, result);
        MessageSource fetched = repository.findById("updateId");
        assertEquals(MessageSource.State.INACTIVE, fetched.getState());
    }

    @Test
    void updateState_shouldUpdateState() {
        MessageSource messageSource = createMessageSource("stateId");
        repository.saveOrUpdate(messageSource);

        int result = repository.updateState("stateId", MessageSource.State.INACTIVE);

        assertEquals(1, result);
        MessageSource fetched = repository.findById("stateId");
        assertEquals(MessageSource.State.INACTIVE, fetched.getState());
    }

    @Test
    void updateConfiguration_shouldUpdateConfiguration() throws JsonProcessingException {
        MessageSource messageSource = createMessageSource("configId");
        repository.saveOrUpdate(messageSource);

        ObjectNode newConfig = new ObjectNode(JsonNodeFactory.instance);
        newConfig.put("newKey", new ObjectNode(JsonNodeFactory.instance));

        int result = repository.updateConfiguration("configId", newConfig);

        assertEquals(1, result);
        MessageSource fetched = repository.findById("configId");
        assertEquals(newConfig, fetched.getConfiguration());
    }

    @Test
    void findAll_shouldReturnAllMessageSources() throws JsonProcessingException {
        repository.saveOrUpdate(createMessageSource("id1"));
        repository.saveOrUpdate(createMessageSource("id2"));

        List<MessageSource> all = repository.findAll();

        assertEquals(2, all.size());
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
}

