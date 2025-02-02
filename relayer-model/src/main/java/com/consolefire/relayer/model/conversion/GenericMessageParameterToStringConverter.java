package com.consolefire.relayer.model.conversion;

import com.consolefire.relayer.util.converter.DefaultObjectToStringConverter;

public class GenericMessageParameterToStringConverter<S>
    extends DefaultObjectToStringConverter<S>
    implements MessagePayloadConverter<S>, MessageHeaderConverter<S>, MessageMetadataConverter<S> {

}
