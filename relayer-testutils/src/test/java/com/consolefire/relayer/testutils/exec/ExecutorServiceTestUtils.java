package com.consolefire.relayer.testutils.exec;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

public class ExecutorServiceTestUtils {

    private static int corePoolSize = 4;
    private static int maxPoolSize = 16;
    private static long keepAliveTime = 1000;
    private static TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    private static int capacity = Integer.MAX_VALUE - 1;

    public static ExecutorService createReaderCheckpointHandlerExecutorService() {
        return getThreadPoolExecutor(
            capacity, "CHECKPOINT-INDEX",
            corePoolSize, maxPoolSize, keepAliveTime, timeUnit);
    }

    public static ExecutorService createParallelTaskExecutorService() {
        return getThreadPoolExecutor(
            capacity, "RANDOM-EXEC",
            corePoolSize, maxPoolSize, keepAliveTime, timeUnit);
    }

    public static ExecutorService createCheckpointCompletedEventExecutorService() {
        return getThreadPoolExecutor(
            100, "CHECKPOINT-COMPLETED",
            corePoolSize, maxPoolSize, keepAliveTime, timeUnit);
    }

    private static ThreadPoolExecutor getThreadPoolExecutor(int capacity, String thredNamePrefix, int corePoolSize,
        int maxPoolSize, long keepAliveTime, TimeUnit timeUnit) {
        BlockingQueue<Runnable> workerQueue = new LinkedBlockingQueue<>(capacity);
        ThreadFactory threadFactory = new BasicThreadFactory.Builder()
            .namingPattern(thredNamePrefix + "-%d")
            .daemon(true)
            .build();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            timeUnit,
            workerQueue,
            threadFactory
        );
        threadPoolExecutor.allowCoreThreadTimeOut(false);
        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return threadPoolExecutor;
    }

    // From: https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/ExecutorService.html
    public static void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ex) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

}
