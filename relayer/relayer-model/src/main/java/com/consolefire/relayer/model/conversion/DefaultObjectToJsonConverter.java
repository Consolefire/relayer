package com.consolefire.relayer.model.conversion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DefaultObjectToJsonConverter<S>
        extends GenericConverter<S, String>
        implements MessagePayloadConverter<S>, MessageHeaderConverter<S>, MessageMetadataConverter<S> {

    private final ObjectMapper objectMapper;

    public DefaultObjectToJsonConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected String doInConvert(S s) {
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(s);
        } catch (JsonProcessingException e) {
            throw new UnsupportedConversionException(e);
        }
        return jsonString;
    }
}
