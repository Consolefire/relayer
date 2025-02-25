package com.consolefire.relayer.outbox.core.props;

import com.consolefire.relayer.core.msgsrc.MessageSourceProvider.Type;
import com.consolefire.relayer.model.source.MessageSourceProperties;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboxConfigProperties {

    private MessageSourceProperties messageSource;
    private OutboxMultiSourceProperties multiSource;
    private MessageReaderProperties messageReader;

    public Set<MessageSourceProperties> getMessageSourceProperties() {
        if (isMultiSourceEnabled()) {
            MessageSourceProviderProperties messageSourceProviderProperties
                = getMultiSource().getMessageSourceProvider();
            if (null == messageSourceProviderProperties) {
                throw new RuntimeException("No MessageSourceProvider configured");
            }
            // only for FIXED type
            if (Type.FIXED.equals(messageSourceProviderProperties.getType())) {
                if (null == messageSourceProviderProperties.getFixed()) {
                    throw new RuntimeException("No fixed message sources configured");
                }
                return messageSourceProviderProperties.getFixed().getMessageSources();
            }
            // TODO: other source config

        } else {
            return Set.of(messageSource);
        }

        return Set.of();
    }

    private boolean isMultiSourceEnabled() {
        return Optional.ofNullable(getMultiSource())
            .map(m -> m.isEnabled()).orElse(false);
    }
}
