package com.consolefire.relayer.model.conversion;

import com.consolefire.relayer.util.converter.DefaultObjectToJsonStringConverter;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GenericMessageParameterToJsonStringConverter<S>
    extends DefaultObjectToJsonStringConverter<S>
    implements MessagePayloadConverter<S>, MessageHeaderConverter<S>, MessageMetadataConverter<S> {

    public GenericMessageParameterToJsonStringConverter(ObjectMapper objectMapper) {
        super(objectMapper);
    }
}
