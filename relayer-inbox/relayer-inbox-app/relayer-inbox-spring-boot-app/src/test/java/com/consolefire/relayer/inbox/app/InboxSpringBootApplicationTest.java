package com.consolefire.relayer.inbox.app;

import static org.junit.jupiter.api.Assertions.*;

import com.consolefire.relayer.inbox.app.cfg.props.RelayerInboxConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class InboxSpringBootApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private RelayerInboxConfigProperties relayerInboxConfigProperties;

    @Test
    void shouldLoadApplicationContext() {
        assertNotNull(applicationContext);
        log.info("Context loads ...");
    }

    @Test
    void shouldLoadRelayerInboxProperties() {
        assertNotNull(relayerInboxConfigProperties);
    }

}