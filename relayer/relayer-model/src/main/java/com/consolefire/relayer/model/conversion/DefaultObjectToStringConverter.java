package com.consolefire.relayer.model.conversion;

public class DefaultObjectToStringConverter<S>
        extends GenericConverter<S, String>
        implements MessagePayloadConverter<S>, MessageHeaderConverter<S>, MessageMetadataConverter<S> {
    @Override
    protected String doInConvert(S s) {
        if (s instanceof String) {
            return (String) s;
        }
        return s.toString();
    }
}
