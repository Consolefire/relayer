package ut.com.consolefire.relayer.core.checkpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.consolefire.relayer.core.checkpoint.ReaderCheckpoint;
import org.junit.jupiter.api.Test;

public class CheckpointTests {


    @Test
    void testMessageSourceCheckpointEqualityAndHashCodeAreEqual() {
        ReaderCheckpoint cp1 = ReaderCheckpoint.builder()
                .identifier("identifier")
                .build();
        ReaderCheckpoint cp2 = ReaderCheckpoint.builder()
                .identifier("identifier")
                .build();

        assertTrue(cp1.equals(cp2));
        assertEquals(cp1.hashCode(), cp2.hashCode());
    }

}
