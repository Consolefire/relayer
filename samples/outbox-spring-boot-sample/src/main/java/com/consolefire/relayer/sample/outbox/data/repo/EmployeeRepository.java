package com.consolefire.relayer.sample.outbox.data.repo;

import com.consolefire.relayer.sample.outbox.data.Employee;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {


}
