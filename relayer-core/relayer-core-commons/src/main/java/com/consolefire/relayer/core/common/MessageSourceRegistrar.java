package com.consolefire.relayer.core.common;

import com.consolefire.relayer.model.source.MessageSourceProperties;

public interface MessageSourceRegistrar {

    MessageSource register(MessageSourceProperties messageSourceProperties);

}
