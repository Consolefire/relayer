package com.consolefire.relayer.model.conversion;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.consolefire.relayer.model.TestMessagePayload;
import com.consolefire.relayer.util.converter.JsonConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.Test;

class GenericMessageParameterConversionTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    private MessageHeaderConverter<Map<String, String>> messageHeaderConverter
        = new GenericMessageParameterToJsonStringConverter<>(objectMapper);
    private MessagePayloadConverter<TestMessagePayload> messagePayloadConverter
        = new GenericMessageParameterToJsonStringConverter<>(objectMapper);
    private MessageMetadataConverter<Map<String, Object>> messageMetadataConverter
        = new GenericMessageParameterToStringConverter<>();


    @Test
    void shouldConvertMessageParameterToJson() throws JsonProcessingException {
        TestMessagePayload testMessagePayload = TestMessagePayload.builder()
            .id(1l)
            .name("some name")
            .parameters(Map.of("p1", "v1"))
            .build();
        String payload = messagePayloadConverter.convert(testMessagePayload);
        TestMessagePayload actualPayload = JsonConverter.jsonStringToObject(objectMapper, TestMessagePayload.class,
            payload);
        Map<String, String> headerMap = Map.of("h1", "v1");
        String header = messageHeaderConverter.convert(headerMap);
        Map<String, String> actualHeader = JsonConverter.jsonStringToObject(objectMapper, Map.class, header);
        Map<String, Object> metadatMap = Map.of("mk1", 100l, "mk2", "v2");
        String metadata = messageMetadataConverter.convert(metadatMap);
        assertAll(
            () -> assertNotNull(payload),
            () -> assertNotNull(header),
            () -> assertNotNull(metadata)
        );
    }


}