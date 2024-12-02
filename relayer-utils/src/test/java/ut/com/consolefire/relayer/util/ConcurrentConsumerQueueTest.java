package ut.com.consolefire.relayer.util;

import com.consolefire.relayer.util.ConcurrentConsumerQueue;
import org.junit.jupiter.api.Test;

public class ConcurrentConsumerQueueTest {

    @Test
    void shouldCreateQueueWithCapacity() {
        ConcurrentConsumerQueue<Long> longConcurrentConsumerQueue = new ConcurrentConsumerQueue();
    }

}
