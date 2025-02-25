package com.consolefire.relayer.core.jobs;

import com.consolefire.relayer.model.source.MessageSourceProperties;
import com.consolefire.relayer.util.data.FilterProperties;

public interface MessageReaderJob {

    <F extends FilterProperties> void execute(MessageSourceProperties messageSourceProperties, F filterProperties);

}
