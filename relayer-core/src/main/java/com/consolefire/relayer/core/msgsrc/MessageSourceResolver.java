package com.consolefire.relayer.core.msgsrc;

import com.consolefire.relayer.msgsrc.MessageSourceContext;

public interface MessageSourceResolver {

    MessageSourceContext resolve(String identifier);
}
