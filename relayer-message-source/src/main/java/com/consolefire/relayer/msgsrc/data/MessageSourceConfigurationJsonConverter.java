package com.consolefire.relayer.msgsrc.data;

import com.consolefire.relayer.util.converter.Converter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class MessageSourceConfigurationJsonConverter implements Converter<String, JsonNode> {

    private final ObjectMapper objectMapper;

    public MessageSourceConfigurationJsonConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public JsonNode convert(String source) {
        if (source == null || source.trim().isEmpty()) {
            return objectMapper.createObjectNode(); // Return an empty JSON object if the source is null or empty
        }
        try {
            return objectMapper.readTree(source);
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON string to JsonNode", e);
        }
    }
}