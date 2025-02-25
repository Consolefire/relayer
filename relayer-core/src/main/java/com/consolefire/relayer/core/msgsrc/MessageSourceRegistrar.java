package com.consolefire.relayer.core.msgsrc;

import com.consolefire.relayer.model.source.MessageSourceProperties;

public interface MessageSourceRegistrar {

    MessageSourceContext register(MessageSourceProperties messageSourceProperties);

}
