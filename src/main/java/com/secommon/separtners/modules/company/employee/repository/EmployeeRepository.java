package com.secommon.separtners.modules.company.employee.repository;

import com.secommon.separtners.modules.company.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeRepositoryQuerydsl {
}
