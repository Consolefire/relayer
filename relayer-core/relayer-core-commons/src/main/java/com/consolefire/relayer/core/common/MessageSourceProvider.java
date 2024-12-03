package com.consolefire.relayer.core.common;

import java.util.List;

public interface MessageSourceProvider {

    MessageSource getMessageSource(String identifier);

    List<MessageSource> getAllRegisteredSources();

}
