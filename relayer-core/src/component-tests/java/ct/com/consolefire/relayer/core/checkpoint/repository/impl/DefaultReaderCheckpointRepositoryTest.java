package ct.com.consolefire.relayer.core.checkpoint.repository.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.consolefire.relayer.core.checkpoint.ReaderCheckpoint;
import com.consolefire.relayer.core.checkpoint.repository.ReaderCheckpointRepository;
import com.consolefire.relayer.core.checkpoint.repository.impl.AbstractReaderCheckpointRepository;
import com.consolefire.relayer.core.checkpoint.repository.impl.DefaultReaderCheckpointQueryProvider;
import com.consolefire.relayer.util.data.DuplicateRecordExistsException;
import com.consolefire.relayer.testutils.data.H2InMemoryDataSourceBuilder;
import com.consolefire.relayer.testutils.data.TestDataSource;
import com.consolefire.relayer.testutils.ext.DataSourceAwareExtension;
import com.radcortez.flyway.test.annotation.FlywayTest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@DataSourceAwareExtension
@FlywayTest(value = @com.radcortez.flyway.test.annotation.DataSource(H2InMemoryDataSourceBuilder.class),
    additionalLocations = {"db/test-migrations"})
class DefaultReaderCheckpointRepositoryTest {

    private static final Instant NOW = Instant.now();
    private static final Instant EXPIRES_AT = NOW.plus(10, ChronoUnit.SECONDS);
    private static final String EXISTING_CP_ID = UUID.randomUUID().toString();
    private static final ReaderCheckpoint EXISTING_CHECKPOINT = ReaderCheckpoint.builder()
        .identifier(EXISTING_CP_ID)
        .completed(true)
        .createdAt(NOW)
        .expiresAt(EXPIRES_AT)
        .build();

    @TestDataSource
    private DataSource dataSource;

    private ReaderCheckpointRepository readerCheckpointRepository;

    @BeforeAll
    void init() {
        assertNotNull(dataSource, "datasource not exists");
        readerCheckpointRepository = new AbstractReaderCheckpointRepository(
            new DefaultReaderCheckpointQueryProvider()) {
            @Override
            protected DataSource getDataSource() {
                return dataSource;
            }
        };
    }

    @BeforeEach
    void setup() {

    }

    @Test
    void shouldInsertNewCheckpoint() {
        Instant now = Instant.now();
        ReaderCheckpoint toInsert = ReaderCheckpoint.builder()
            .identifier(UUID.randomUUID().toString())
            .createdAt(now)
            .expiresAt(now.plus(10, ChronoUnit.SECONDS))
            .build();
        ReaderCheckpoint actual = readerCheckpointRepository.insert(toInsert);
        assertNotNull(actual);
    }

    @Test
    void shouldDeleteWhenExists() {
        Instant now = Instant.now();
        ReaderCheckpoint toInsert = ReaderCheckpoint.builder()
            .identifier(UUID.randomUUID().toString())
            .createdAt(now)
            .expiresAt(now.plus(10, ChronoUnit.SECONDS))
            .build();
        ReaderCheckpoint inserted = readerCheckpointRepository.insert(toInsert);
        readerCheckpointRepository.deleteByIdentifier(toInsert.getIdentifier());
        ReaderCheckpoint actual = readerCheckpointRepository.findByIdentifier(toInsert.getIdentifier());
        assertNull(actual);
    }

    @Test
    void shouldFailToInsertWhileExists() {
        readerCheckpointRepository.insert(EXISTING_CHECKPOINT);
        assertThrows(DuplicateRecordExistsException.class,
            () -> readerCheckpointRepository.insert(EXISTING_CHECKPOINT));
        readerCheckpointRepository.deleteByIdentifier(EXISTING_CP_ID);
    }

    @Test
    void shouldUpdateExistingRecord() {
        Instant now = Instant.now();
        ReaderCheckpoint toInsert = ReaderCheckpoint.builder()
            .identifier(UUID.randomUUID().toString())
            .createdAt(now)
            .expiresAt(now.plus(10, ChronoUnit.SECONDS))
            .build();
        ReaderCheckpoint insertedCp = readerCheckpointRepository.insert(toInsert);
        assertNotNull(insertedCp);
        assertFalse(insertedCp.isCompleted());
        ReaderCheckpoint updated = insertedCp.withCompleted(true);
        assertNotNull(updated);
        assertTrue(updated.isCompleted());
    }
}