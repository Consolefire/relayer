package com.consolefire.relayer.model.source;

import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MessageSource {

    private final String identifier;
    private Map<String, Object> configuration;

}
