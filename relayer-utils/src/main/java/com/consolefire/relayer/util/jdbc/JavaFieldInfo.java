package com.consolefire.relayer.util.jdbc;

import lombok.Builder;
import lombok.NonNull;
import lombok.With;

import java.io.Serializable;
import java.util.Objects;

@Builder
public record JavaFieldInfo(
        @NonNull Class<?> enclosingType,
        @With @NonNull String fieldName,
        @With @NonNull Class<?> fieldType) implements Serializable {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JavaFieldInfo that)) return false;
        return Objects.equals(fieldName, that.fieldName) && Objects.equals(enclosingType, that.enclosingType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enclosingType, fieldName);
    }
}
