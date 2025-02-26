package com.consolefire.relayer.core.msgsrc;

import com.consolefire.relayer.model.source.MessageSourceProperties;
import com.consolefire.relayer.msgsrc.MessageSourceContext;

public interface MessageSourceRegistrar {

    MessageSourceContext register(MessageSourceProperties messageSourceProperties);

}
