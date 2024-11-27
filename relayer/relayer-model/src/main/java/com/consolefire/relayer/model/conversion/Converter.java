package com.consolefire.relayer.model.conversion;

public interface Converter<SOURCE, TARGET> {

    TARGET convert(SOURCE source);

}
