package com.consolefire.relayer.core.data.utils;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import org.junit.jupiter.api.Test;

public class MessageFilterPropertiesTest {

    @Test
    void shouldCreateMessageFilterProperties() {
        MessageFilterProperties<Long> properties = MessageFilterProperties.<Long>builder()
            .messageId(1001L)
            .build();
        assertNotNull(properties);
        MessageFilterProperties<Long> newProperties = properties.withCreatedAt(Instant.now());
        assertAll(
            () -> assertNotNull(newProperties),
            () -> assertEquals(properties.getMessageId(), newProperties.getMessageId()),
            () -> assertTrue(null == properties.getCreatedAt()),
            () -> assertNotNull(newProperties.getCreatedAt())
        );
    }

}
