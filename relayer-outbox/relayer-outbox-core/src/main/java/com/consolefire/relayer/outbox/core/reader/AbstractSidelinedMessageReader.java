package com.consolefire.relayer.outbox.core.reader;

import com.consolefire.relayer.core.data.ParkedMessageReadQueryProvider;
import com.consolefire.relayer.core.reader.MessageFilterProperties;
import com.consolefire.relayer.model.source.MessageSourceProperties;
import com.consolefire.relayer.outbox.model.SidelinedMessage;
import com.consolefire.relayer.util.data.DataSourceResolver;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AbstractSidelinedMessageReader<ID extends Serializable>
    implements SidelinedMessageReader<ID> {

    private final DataSourceResolver dataSourceResolver;
    private final ParkedMessageReadQueryProvider parkedMessageReadQueryProvider;

    @Override
    public Collection<SidelinedMessage<ID>> read(MessageSourceProperties messageSourceProperties,
        Optional<MessageFilterProperties> messageFilterProperties) {
        return List.of();
    }
}
