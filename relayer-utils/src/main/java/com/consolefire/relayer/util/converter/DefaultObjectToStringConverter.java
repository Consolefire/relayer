package com.consolefire.relayer.util.converter;

public class DefaultObjectToStringConverter<S>
    extends GenericConverter<S, String> {

    @Override
    protected String doInConvert(S s) {
        if (s instanceof String) {
            return (String) s;
        }
        return s.toString();
    }
}
