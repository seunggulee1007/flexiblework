package com.secommon.separtners.modules.company.department.repository;

import com.secommon.separtners.modules.company.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long>, DepartmentRepositoryQuerydsl {

}
