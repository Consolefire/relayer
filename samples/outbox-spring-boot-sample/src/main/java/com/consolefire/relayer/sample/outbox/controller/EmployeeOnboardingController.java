package com.consolefire.relayer.sample.outbox.controller;

import com.consolefire.relayer.sample.outbox.CurrentTenantContext;
import com.consolefire.relayer.sample.outbox.data.EmployeeDto;
import com.consolefire.relayer.sample.outbox.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeOnboardingController {

    private final EmployeeService employeeService;

    @PostMapping("/onboard")
    public ResponseEntity<EmployeeDto> onboardEmployee(
        @RequestHeader(name = "x-tenant-id") String tenantId,
        @RequestBody EmployeeOnboardRequest request) {
        CurrentTenantContext.setCurrentTenant(tenantId);
        EmployeeDto employeeDto = employeeService.onboard(tenantId, request.getName());
        CurrentTenantContext.clearCurrentTenant();
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeDto);
    }
}
