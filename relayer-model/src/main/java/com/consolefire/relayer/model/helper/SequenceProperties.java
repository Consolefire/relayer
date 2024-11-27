package com.consolefire.relayer.model.helper;

import lombok.NonNull;

public record SequenceProperties(
        @NonNull String schemaName,
        @NonNull String name,
        @NonNull Long startWith,
        @NonNull Long minValue,
        @NonNull Long maxValue,
        @NonNull Boolean cycle) {

}
