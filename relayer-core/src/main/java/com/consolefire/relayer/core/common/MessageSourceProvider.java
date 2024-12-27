package com.consolefire.relayer.core.common;

import java.util.List;

public interface MessageSourceProvider {

    MessageSourceContext getMessageSource(String identifier);

    List<MessageSourceContext> getAllRegisteredSources();

}
