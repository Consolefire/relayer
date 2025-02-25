package com.consolefire.relayer.util.props;

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
public class SchedulerProperties {

    private boolean autoStart;
    private String jobPrefix;
    private Schedule schedule;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Schedule {

        private Cron cron;
        private Fixed fixed;

    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Cron {

        private String expression;

    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Fixed {

        private int initialDelay;
        private int delay;
        private int rate;

    }

}
