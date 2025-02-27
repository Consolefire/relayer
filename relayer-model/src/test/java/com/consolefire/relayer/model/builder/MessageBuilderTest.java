package com.consolefire.relayer.model.builder;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.consolefire.relayer.model.MessageState;
import com.consolefire.relayer.model.TestMessage;
import com.consolefire.relayer.model.TestMessagePayload;
import com.consolefire.relayer.model.conversion.GenericMessageParameterToJsonStringConverter;
import com.consolefire.relayer.model.helper.InMemoryMessageSequenceGenerator;
import com.consolefire.relayer.model.helper.RandomLongMessageIdGenerator;
import com.consolefire.relayer.model.helper.RandomMessageGroupIdGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class MessageBuilderTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    void shouldBuildMessage() {

        TestMessageBuilder messageBuilder = new TestMessageBuilder();

        int testCount = 10001;
        String channelName = "ch01";
        TestMessage message = messageBuilder
            .withMessageId(1001l)
            .withMessageSequence(1l)
            .withGroupId(UUID.randomUUID().toString())
            .withTestCount(testCount)
            .withChannelName(channelName)
            .withPayload(TestMessagePayload.random())
            .withHeaders(Map.of("Some-header", "some value"))
            .withMetadata(Map.of("Meta", "date"))
            .withState(MessageState.NEW)
            .withCreatedAt(Instant.now())
            .withUpdatedAt(Instant.now())
            .build();

        assertNotNull(message);

        assertAll(
            () -> assertNotNull(message),
            () -> assertNotNull(message.getMessageId()),
            () -> assertNotNull(message.getMessageSequence()),
            () -> assertNotNull(message.getGroupId()),
            () -> {
                assertTrue(message instanceof TestMessage);
                assertEquals(testCount, message.getTestCount());
            }
        );
    }

    @Test
    void shouldBuildMessageWithSuppliers() {

        TestMessageBuilder messageBuilder = new TestMessageBuilder();

        int testCount = 10001;
        String channelName = "ch01";
        TestMessage message = messageBuilder
            .withMessageIdSupplier(()->1001l)
            .withMessageSequenceSupplier(()->213l)
            .withGroupId(UUID.randomUUID().toString())
            .withTestCount(testCount)
            .withChannelName(channelName)
            .withPayload(TestMessagePayload.random())
            .withHeaders(Map.of("Some-header", "some value"))
            .withMetadata(Map.of("Meta", "date"))
            .withState(MessageState.NEW)
            .withCreatedAt(Instant.now())
            .withUpdatedAt(Instant.now())
            .get();

        assertNotNull(message);

        assertAll(
            () -> assertNotNull(message),
            () -> assertNotNull(message.getMessageId()),
            () -> assertNotNull(message.getMessageSequence()),
            () -> assertNotNull(message.getGroupId()),
            () -> {
                assertTrue(message instanceof TestMessage);
                assertEquals(testCount, message.getTestCount());
            }
        );
    }

    @Test
    void shouldBuildMessageUsingGenerators() {

        TestMessageBuilder messageBuilder = new TestMessageBuilder();

        int testCount = 10001;
        String channelName = "ch01";
        TestMessage message = messageBuilder
            .usingMessageIdGenerator(new RandomLongMessageIdGenerator())
            .usingMessageSequenceGenerator(new InMemoryMessageSequenceGenerator())
            .usingMessageGroupIdGenerator(new RandomMessageGroupIdGenerator())
            .withTestCount(testCount)
            .withChannelName(channelName)
            .withPayload(TestMessagePayload.random())
            .withHeaders(Map.of("Some-header", "some value"))
            .withMetadata(Map.of("Meta", "date"))
            .withState(MessageState.NEW)
            .withCreatedAt(Instant.now())
            .withUpdatedAt(Instant.now())
            .build();

        assertNotNull(message);

        assertAll(
            () -> assertNotNull(message),
            () -> assertNotNull(message.getMessageId()),
            () -> assertNotNull(message.getMessageSequence()),
            () -> assertNotNull(message.getGroupId()),
            () -> {
                assertTrue(message instanceof TestMessage);
                assertEquals(testCount, message.getTestCount());
            }
        );
    }

    @Test
    void shouldBuildMessageUsingGeneratorsAndConverters() {

        TestMessageBuilder messageBuilder = new TestMessageBuilder();

        int testCount = 10001;
        String channelName = "ch01";
        TestMessage message = messageBuilder
            .usingMessageIdGenerator(new RandomLongMessageIdGenerator())
            .usingMessageSequenceGenerator(new InMemoryMessageSequenceGenerator())
            .usingMessageGroupIdGenerator(new RandomMessageGroupIdGenerator())
            .usingMessagePayloadConverter(new GenericMessageParameterToJsonStringConverter<>(OBJECT_MAPPER))
            .usingMessageHeaderConverter(new GenericMessageParameterToJsonStringConverter<>(OBJECT_MAPPER))
            .usingMessageMetadataConverter(new GenericMessageParameterToJsonStringConverter<>(OBJECT_MAPPER))
            .withTestCount(testCount)
            .withChannelName(channelName)
            .withPayload(TestMessagePayload.random())
            .withHeaders(Map.of("Some-header", "some value"))
            .withMetadata(Map.of("Meta", "date"))
            .withState(MessageState.NEW)
            .withCreatedAt(Instant.now())
            .withUpdatedAt(Instant.now())
            .build();

        assertNotNull(message);

        assertAll(
            () -> assertNotNull(message),
            () -> assertNotNull(message.getMessageId()),
            () -> assertNotNull(message.getMessageSequence()),
            () -> assertNotNull(message.getGroupId()),
            () -> {
                assertTrue(message instanceof TestMessage);
                assertEquals(testCount, message.getTestCount());
            }
        );
    }
}