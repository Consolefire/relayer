package com.consolefire.relayer.util.query;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@With
@Getter
@Builder
@RequiredArgsConstructor
public class Parameter<T> {

    private final int index;
    private final String name;
    private final T value;

}
