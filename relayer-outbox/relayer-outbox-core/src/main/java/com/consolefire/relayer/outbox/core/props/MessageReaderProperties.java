package com.consolefire.relayer.outbox.core.props;

import com.consolefire.relayer.util.props.SchedulerProperties;
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
public class MessageReaderProperties {

    private boolean enabled = true;
    private Outbound outbound;
    private Sidelined sidelined;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Outbound {

        private boolean enabled = true;
        private SchedulerProperties scheduler;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sidelined {

        private boolean enabled = true;
        private SchedulerProperties scheduler;
    }
}
