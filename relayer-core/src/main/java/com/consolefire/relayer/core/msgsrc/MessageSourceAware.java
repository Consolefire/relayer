package com.consolefire.relayer.core.msgsrc;

import com.consolefire.relayer.msgsrc.MessageSourceContext;

public interface MessageSourceAware {

    void setMessageSource(MessageSourceContext messageSourceContext);

}
