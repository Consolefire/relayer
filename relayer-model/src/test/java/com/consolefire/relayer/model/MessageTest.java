package com.consolefire.relayer.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MessageTest {

    @Test
    void shouldCompareMessages() {
        SimpleTestMessage m1 = new SimpleTestMessage();
        m1.setMessageSequence(1l);
        SimpleTestMessage m11 = new SimpleTestMessage();
        m11.setMessageSequence(1l);

        SimpleTestMessage m2 = new SimpleTestMessage();
        m2.setMessageSequence(2l);

        SimpleTestMessage m3 = new SimpleTestMessage();

        assertAll(
            () -> assertEquals(-1, m1.compareTo(m2)),
            () -> assertEquals(-1, new SimpleTestMessage().compareTo(m1)),
            () -> assertEquals(1, m1.compareTo(m3)),
            () -> assertEquals(1, m1.compareTo(null)),
            () -> assertEquals(0, m1.compareTo(m11))
        );


    }


}