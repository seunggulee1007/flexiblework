package com.secommon.separtners.modules.company.employeemanagement;

import com.secommon.separtners.infra.commons.BaseServiceAnnotation;
import com.secommon.separtners.modules.company.department.repository.DepartmentRepository;
import com.secommon.separtners.modules.company.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

@BaseServiceAnnotation
@RequiredArgsConstructor
public class EmployeeManagementService {

    private final EmployeeManagementRepository employeeManagementRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;


}
