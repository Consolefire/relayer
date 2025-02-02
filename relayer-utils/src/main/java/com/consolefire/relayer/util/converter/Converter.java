package com.consolefire.relayer.util.converter;

@FunctionalInterface
public interface Converter<SOURCE, TARGET> {

    TARGET convert(SOURCE source);

}
