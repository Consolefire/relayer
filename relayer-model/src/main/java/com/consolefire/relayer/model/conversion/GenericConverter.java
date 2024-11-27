package com.consolefire.relayer.model.conversion;

import java.util.Optional;

public abstract class GenericConverter<SOURCE, TARGET> implements Converter<SOURCE, TARGET> {

    @Override
    public TARGET convert(SOURCE source) {
        return Optional.ofNullable(source)
                .map(s -> {
                    try {
                        return doInConvert(source);
                    } catch (Exception exception) {
                        throw new UnsupportedConversionException(exception);
                    }
                }).orElse(null);
    }

    protected abstract TARGET doInConvert(SOURCE source);

}
