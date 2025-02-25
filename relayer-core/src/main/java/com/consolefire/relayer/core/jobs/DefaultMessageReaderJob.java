package com.consolefire.relayer.core.jobs;

import com.consolefire.relayer.model.source.MessageSourceProperties;
import com.consolefire.relayer.util.data.FilterProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DefaultMessageReaderJob implements MessageReaderJob {



    @Override
    public <F extends FilterProperties> void execute(MessageSourceProperties messageSourceProperties,
        F filterProperties) {
        log.info("Starting message reader job.");
    }
}
