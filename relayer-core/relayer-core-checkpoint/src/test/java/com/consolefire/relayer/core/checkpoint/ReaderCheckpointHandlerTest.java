package com.consolefire.relayer.core.checkpoint;

import static com.consolefire.relayer.testutils.exec.ExecutorServiceTestUtils.createCheckpointCompletedEventExecutorService;
import static com.consolefire.relayer.testutils.exec.ExecutorServiceTestUtils.createParallelTaskExecutorService;
import static com.consolefire.relayer.testutils.exec.ExecutorServiceTestUtils.createReaderCheckpointHandlerExecutorService;
import static com.consolefire.relayer.testutils.exec.ExecutorServiceTestUtils.shutdownAndAwaitTermination;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.consolefire.relayer.core.checkpoint.repository.ReaderCheckpointRepository;
import com.consolefire.relayer.core.checkpoint.repository.impl.AbstractReaderCheckpointRepository;
import com.consolefire.relayer.core.checkpoint.repository.impl.DefaultReaderCheckpointQueryProvider;
import com.consolefire.relayer.core.checkpoint.service.ReaderCheckpointService;
import com.consolefire.relayer.core.checkpoint.service.impl.DefaultReaderCheckpointService;
import com.consolefire.relayer.core.checkpoint.task.CheckpointCompletedEvent;
import com.consolefire.relayer.core.checkpoint.task.ReaderCheckpointCompletedConsumer;
import com.consolefire.relayer.testutils.data.TestDataSource;
import com.consolefire.relayer.testutils.exec.ExecutorServiceTestUtils;
import com.consolefire.relayer.testutils.ext.DataSourceAwareExtension;
import com.consolefire.relayer.testutils.ext.TestLoggerExtension;
import com.consolefire.relayer.util.ConcurrentConsumerQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Slf4j
@Disabled
@TestLoggerExtension
@DataSourceAwareExtension
class ReaderCheckpointHandlerTest {

    private static final Random RANDOM = new Random(1);
    private static final String SRC_ID_1 = UUID.randomUUID().toString();
    private static final String SRC_ID_2 = UUID.randomUUID().toString();

    private static final ConcurrentConsumerQueue<CheckpointCompletedEvent> cpEventQueue1
        = new ConcurrentConsumerQueue<>();
    private static final ConcurrentConsumerQueue<CheckpointCompletedEvent> cpEventQueue2
        = new ConcurrentConsumerQueue<>();

    @TestDataSource
    private DataSource dataSource;

    private ReaderCheckpointRepository readerCheckpointRepository;
    private ReaderCheckpointService readerCheckpointService;
    private ReaderCheckpointHandler readerCheckpointHandler;
    private ExecutorService checkpointConsumerExecutorService = createReaderCheckpointHandlerExecutorService();
    private ExecutorService cpEventExecutorService = createCheckpointCompletedEventExecutorService();
    private Object terminationLock = new Object();
    private ExecutorService producerExecutor = createParallelTaskExecutorService();

    @BeforeAll
    void init() {
        assertNotNull(dataSource, "datasource not exists");
        String sql = """
            create table READER_CHECKPOINTS (
                IDENTIFIER varchar(256) not null, 
                IS_COMPLETED bool not null, 
                CREATED_AT timestamp not null, 
                EXPIRES_AT timestamp,
                primary key (IDENTIFIER)
            )""";
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            boolean result = statement.execute();
            log.info("table created: ", result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        readerCheckpointRepository = new AbstractReaderCheckpointRepository(
            new DefaultReaderCheckpointQueryProvider()) {
            @Override
            protected DataSource getDataSource() {
                return dataSource;
            }
        };
        readerCheckpointService = new DefaultReaderCheckpointService(readerCheckpointRepository);
        readerCheckpointHandler = new ReaderCheckpointHandler(readerCheckpointService,
            this.checkpointConsumerExecutorService, Map.of(SRC_ID_1, cpEventQueue1, SRC_ID_2, cpEventQueue2));

        cpEventExecutorService.submit(
            new ReaderCheckpointCompletedConsumer(SRC_ID_1, readerCheckpointHandler, cpEventQueue1));
        cpEventExecutorService.submit(
            new ReaderCheckpointCompletedConsumer(SRC_ID_2, readerCheckpointHandler, cpEventQueue2));
    }

    @AfterAll
    void destroy() throws InterruptedException {
        shutdownExecutors();
    }


    void shutdownExecutors() throws InterruptedException {
        producerExecutor.shutdown();
        shutdownAndAwaitTermination(checkpointConsumerExecutorService);
        shutdownAndAwaitTermination(cpEventExecutorService);
    }

    @Test
    void someTest() {
        readerCheckpointHandler.initialize(SRC_ID_1);
        readerCheckpointHandler.initialize(SRC_ID_2);
        final int expectedTotal = 10;
        IntStream.rangeClosed(1, expectedTotal)
            .parallel()
            .forEach(i ->
                {
                    producerExecutor.submit(
                        () -> {
                            readerCheckpointHandler.process(SRC_ID_1, new CheckpointIndex(i, expectedTotal));
                        });
                    producerExecutor.submit(
                        () -> {
                            readerCheckpointHandler.process(SRC_ID_2, new CheckpointIndex(i, expectedTotal));
                        });
                }
            );
    }

    private static void randomSleep() {
        try {
            Thread.sleep(RANDOM.nextInt(500, 1000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}