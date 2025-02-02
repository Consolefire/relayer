package com.consolefire.relayer.model.conversion;

import com.consolefire.relayer.util.converter.DefaultObjectToJsonConverter;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GenericMessageParameterToJsonConverter<S>
    extends DefaultObjectToJsonConverter<S>
    implements MessagePayloadConverter<S>, MessageHeaderConverter<S>, MessageMetadataConverter<S> {

    public GenericMessageParameterToJsonConverter(ObjectMapper objectMapper) {
        super(objectMapper);
    }
}
