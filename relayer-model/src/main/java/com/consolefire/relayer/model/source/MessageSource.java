package com.consolefire.relayer.model.source;

import com.consolefire.relayer.model.Message;
import java.io.Serializable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class MessageSource<ID extends Serializable, M extends Message<ID>> {

    private final String identifier;

}
