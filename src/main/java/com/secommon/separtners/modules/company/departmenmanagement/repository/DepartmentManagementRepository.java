package com.secommon.separtners.modules.company.departmenmanagement.repository;

import com.secommon.separtners.modules.company.departmenmanagement.DepartmentManagement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentManagementRepository extends JpaRepository<DepartmentManagement, Long>, DepartmentManagementRepositoryQuerydsl {

}
