package com.consolefire.relayer.model.source;

import com.consolefire.relayer.model.data.StoreType;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MessageSourceProperties {

    private final String identifier;
    private final StoreType storeType;
    private final Map<String, String> properties;

}
