package com.consolefire.relayer.outbox.app.cfg.props;

import lombok.Builder;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "relayer.outbox")
public class RelayerOutboxConfigProperties {

    private final String profile;
    private final ConfigProperties config;

    @Builder
    @ConstructorBinding
    public RelayerOutboxConfigProperties(String profile, ConfigProperties config) {
        this.profile = profile;
        this.config = config;
    }

    @Getter
    @ConfigurationProperties(prefix = "config")
    public static class ConfigProperties {

        private final String location;
        private final boolean failOnNotFound;
        private final boolean failOnNotAccessible;

        @Builder
        @ConstructorBinding
        public ConfigProperties(String location, boolean failOnNotFound, boolean failOnNotAccessible) {
            this.location = location;
            this.failOnNotFound = failOnNotFound;
            this.failOnNotAccessible = failOnNotAccessible;
        }
    }


}
