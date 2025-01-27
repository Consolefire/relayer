package com.consolefire.relayer.outbox.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = {
    "com.consolefire.relayer.outbox.app"
})
@ConfigurationPropertiesScan(basePackages = {"com.consolefire.relayer.outbox.app.cfg.props"})
public class OutboxSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutboxSpringBootApplication.class, args);
    }

}
