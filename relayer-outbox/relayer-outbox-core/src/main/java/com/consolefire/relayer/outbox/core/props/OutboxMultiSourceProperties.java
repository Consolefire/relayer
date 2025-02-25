package com.consolefire.relayer.outbox.core.props;

import com.consolefire.relayer.model.source.MessageSourceProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OutboxMultiSourceProperties {

    private boolean enabled = false;
    private MessageSourceProviderProperties messageSourceProvider;

}
