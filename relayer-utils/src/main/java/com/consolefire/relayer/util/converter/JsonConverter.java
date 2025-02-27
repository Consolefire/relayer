package com.consolefire.relayer.util.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Type;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonConverter {

    private static final ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper();

    public static <T> String toJsonString(T data) throws JsonProcessingException {
        return toJsonString(DEFAULT_OBJECT_MAPPER, data);
    }

    public static <T> String toJsonString(ObjectMapper objectMapper, T data) throws JsonProcessingException {
        if (null == data) {
            return null;
        }
        if (null == objectMapper) {
            objectMapper = DEFAULT_OBJECT_MAPPER;
        }
        return objectMapper.writeValueAsString(data);
    }

    public static <T> JsonNode toJsonNode(ObjectMapper objectMapper, T data) throws JsonProcessingException {
        if (null == data) {
            return null;
        }
        if (null == objectMapper) {
            objectMapper = DEFAULT_OBJECT_MAPPER;
        }
        return objectMapper.valueToTree(data);
    }

    public static <T> T jsonStringToObject(Class<T> clazz, String data) throws JsonProcessingException {
        return jsonStringToObject(DEFAULT_OBJECT_MAPPER, clazz, data);
    }

    public static <T> T jsonStringToObject(ObjectMapper objectMapper, Class<T> clazz, String data)
        throws JsonProcessingException {
        if (null == data || data.isBlank()) {
            return null;
        }
        if (null == objectMapper) {
            objectMapper = DEFAULT_OBJECT_MAPPER;
        }
        return objectMapper.readValue(data, new TypeReference<T>() {
            @Override
            public Type getType() {
                return clazz;
            }
        });
    }

}
