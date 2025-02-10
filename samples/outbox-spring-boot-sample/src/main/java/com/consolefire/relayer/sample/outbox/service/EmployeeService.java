package com.consolefire.relayer.sample.outbox.service;

import com.consolefire.relayer.model.MessageState;
import com.consolefire.relayer.model.source.MessageSourceProperties;
import com.consolefire.relayer.outbox.model.OutboundMessage;
import com.consolefire.relayer.outbox.writer.OutboundMessageWriter;
import com.consolefire.relayer.sample.outbox.data.Employee;
import com.consolefire.relayer.sample.outbox.data.EmployeeDto;
import com.consolefire.relayer.sample.outbox.data.repo.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final ObjectMapper objectMapper;
    private final EmployeeRepository employeeRepository;
    private final OutboundMessageWriter<UUID> outboundMessageWriter;

    @SneakyThrows
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public EmployeeDto onboard(String tenantId, String name) {
        log.info("Tenant: {}", tenantId);
        Employee createdEmployee = employeeRepository.save(Employee.builder()
            .name(name)
            .createdAt(Instant.now())
            .build());
        OutboundMessage<UUID> outboundMessage = OutboundMessage.<UUID>builder()
            .messageId(UUID.randomUUID())
            .groupId("EMPLOYEE")
            .channelName("employee-onboarding")
            .payload(objectMapper.writeValueAsString(createdEmployee))
            .state(MessageState.NEW)
            .build();
        log.info("Write message: {}", outboundMessage);
        outboundMessageWriter.insert(MessageSourceProperties.builder()
            .identifier(tenantId)
            .build(), outboundMessage);
        return EmployeeDto.builder().id(createdEmployee.getId()).name(createdEmployee.getName())
            .createdAt(createdEmployee.getCreatedAt()).build();
    }
}
