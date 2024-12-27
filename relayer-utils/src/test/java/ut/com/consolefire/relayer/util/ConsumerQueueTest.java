package ut.com.consolefire.relayer.util;

import com.consolefire.relayer.util.ConsumerQueue;
import org.junit.jupiter.api.Test;

public class ConsumerQueueTest {

    @Test
    void shouldCreateQueueWithCapacity() {
        ConsumerQueue<Long> longConsumerQueue = new ConsumerQueue();
    }

}
