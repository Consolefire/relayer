package com.consolefire.relayer.outbox.app;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.consolefire.relayer.outbox.app.cfg.props.RelayerOutboxConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class OutboxSpringBootApplicationTest {

    //private static final Logger log = LoggerFactory.getLogger(OutboxSpringBootApplicationTest.class);

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private RelayerOutboxConfigProperties relayerOutboxConfigProperties;

    @Test
    void shouldLoadApplicationContext() {
        assertNotNull(applicationContext);
        log.info("Context loads ...");
    }

    @Test
    void shouldLoadRelayerOutboxProperties() {
        assertNotNull(relayerOutboxConfigProperties);
    }

}