package com.consolefire.relayer.core.common;

import com.consolefire.relayer.model.source.MessageSourceProperties;

public interface MessageSourceRegistrar {

    MessageSourceContext register(MessageSourceProperties messageSourceProperties);

}
