package ut.com.consolefire.relayer.core.checkpoint;

import com.consolefire.relayer.core.checkpoint.MessageSourceCheckpoint;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CheckpointTests {


    @Test
    void testMessageSourceCheckpointEqualityAndHashCodeAreEqual() {
        MessageSourceCheckpoint cp1 = MessageSourceCheckpoint.builder()
                .identifier("identifier")
                .referenceCheckpointId("checkpointReference")
                .readCount(100)
                .build();
        MessageSourceCheckpoint cp2 = MessageSourceCheckpoint.builder()
                .identifier("identifier")
                .referenceCheckpointId("checkpointReference")
                .readCount(100)
                .build();

        assertTrue(cp1.equals(cp2));
        assertEquals(cp1.hashCode(), cp2.hashCode());
        assertTrue(cp1.equals(cp2.withReadCount(111)));
        assertEquals(cp1.hashCode(), cp2.withReadCount(111).hashCode());
    }

    @Test
    void testMessageSourceCheckpointEqualityAndHashCodeAreNotEqual() {
        MessageSourceCheckpoint cp1 = MessageSourceCheckpoint.builder()
                .identifier("identifier_1")
                .referenceCheckpointId("checkpointReference")
                .readCount(100)
                .build();
        MessageSourceCheckpoint cp2 = MessageSourceCheckpoint.builder()
                .identifier("identifier_2")
                .referenceCheckpointId("checkpointReference")
                .readCount(100)
                .build();

        MessageSourceCheckpoint cp3 = MessageSourceCheckpoint.builder()
                .identifier("identifier")
                .referenceCheckpointId("checkpointReference-1")
                .readCount(100)
                .build();
        MessageSourceCheckpoint cp4 = MessageSourceCheckpoint.builder()
                .identifier("identifier")
                .referenceCheckpointId("checkpointReference-2")
                .readCount(100)
                .build();

        assertFalse(cp1.equals(cp2));
        assertNotEquals(cp1.hashCode(), cp2.hashCode());
        assertFalse(cp1.equals(cp2.withReadCount(111)));
        assertNotEquals(cp1.hashCode(), cp2.withReadCount(111).hashCode());
        assertFalse(cp3.equals(cp4));
        assertNotEquals(cp3.hashCode(), cp4.hashCode());
        assertFalse(cp3.equals(cp4.withReadCount(111)));
        assertNotEquals(cp3.hashCode(), cp4.withReadCount(111).hashCode());
    }
}
