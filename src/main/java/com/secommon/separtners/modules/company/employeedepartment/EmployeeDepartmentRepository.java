package com.secommon.separtners.modules.company.mapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface EmployeeDepartmentRepository extends JpaRepository<EmployeeDepartment, Long> {

}
