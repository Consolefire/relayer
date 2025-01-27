package com.consolefire.relayer.inbox.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = {
    "com.consolefire.relayer.inbox.app"
})
@ConfigurationPropertiesScan(basePackages = {"com.consolefire.relayer.inbox.app.cfg.props"})
public class InboxSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(InboxSpringBootApplication.class, args);
    }

}
