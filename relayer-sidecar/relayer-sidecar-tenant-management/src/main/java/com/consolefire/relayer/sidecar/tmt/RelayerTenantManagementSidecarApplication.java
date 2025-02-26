package com.consolefire.relayer.sidecar.tmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.consolefire.relayer.sidecar.tmt"})
public class RelayerTenantManagementSidecarApplication {

    public static void main(String[] args) {
        SpringApplication.run(RelayerTenantManagementSidecarApplication.class, args);
    }

}
