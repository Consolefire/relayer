package com.consolefire.relayer.outbox.core.props;

import com.consolefire.relayer.core.msgsrc.MessageSourceProvider.Type;
import com.consolefire.relayer.model.source.MessageSourceProperties;
import java.util.Set;
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
public class MessageSourceProviderProperties {

    private Type type;
    private Internal internal;
    private External external;
    private Fixed fixed;

    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Internal {

        private String providerClassName;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class External {

        private String url;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Fixed {

        private Set<MessageSourceProperties> messageSources;
    }


}
