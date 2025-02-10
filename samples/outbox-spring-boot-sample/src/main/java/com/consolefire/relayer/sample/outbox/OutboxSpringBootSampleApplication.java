package com.consolefire.relayer.sample.outbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = {"com.consolefire.relayer.sample.outbox"})
@EnableConfigurationProperties({OutboxConfigProperties.class})
public class OutboxSpringBootSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutboxSpringBootSampleApplication.class, args);
    }


}
