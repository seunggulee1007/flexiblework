package com.secommon.separtners.modules.company.department.repository;

import com.secommon.separtners.modules.company.department.Department;
import com.secommon.separtners.modules.company.department.form.DepartmentSearchCondition;

import java.util.List;

public interface DepartmentRepositoryQuerydsl {

    List<Department> findAllDepartment ( DepartmentSearchCondition condition );

}
