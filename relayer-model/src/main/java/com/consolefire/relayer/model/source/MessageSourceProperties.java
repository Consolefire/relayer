package com.consolefire.relayer.model.source;

import com.consolefire.relayer.model.data.StoreType;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageSourceProperties {

    private final String identifier;
    private final StoreType storeType;
    private final Map<String, String> properties;

    public MessageSourceProperties(String identifier, StoreType storeType) {
        this.identifier = identifier;
        this.storeType = storeType;
        this.properties = new HashMap<>();
    }

    @Builder
    public MessageSourceProperties(String identifier, StoreType storeType, Map<String, String> properties) {
        this.identifier = identifier;
        this.storeType = storeType;
        this.properties = properties;
    }
}
