package com.consolefire.relayer.sample.outbox;

import com.consolefire.relayer.outbox.core.props.OutboxConfigProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@Slf4j
//@Profile("single-source")
@SpringBootApplication(scanBasePackages = {"com.consolefire.relayer.sample.outbox"})
public class SpringBootOutboxRelayerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootOutboxRelayerApplication.class, args);
    }

    @Autowired
    private OutboxConfigProperties outboxConfigProperties;
    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    @PostConstruct
    void printConfig() {
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(outboxConfigProperties));
    }
}
