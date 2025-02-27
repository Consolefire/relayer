package com.consolefire.relayer.util.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DefaultObjectToJsonStringConverter<SOURCE>
    extends GenericConverter<SOURCE, String> {

    private final ObjectMapper objectMapper;

    public DefaultObjectToJsonStringConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected String doInConvert(SOURCE source) {
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            throw new UnsupportedConversionException(e);
        }
        return jsonString;
    }

}
