package com.secommon.separtners.modules.company.mapping;

import com.secommon.separtners.infra.commons.BaseServiceAnnotation;
import com.secommon.separtners.modules.company.department.Department;
import com.secommon.separtners.modules.company.employee.Employee;
import lombok.RequiredArgsConstructor;

@BaseServiceAnnotation
@RequiredArgsConstructor
public class EmployeeDepartmentService {

    private final EmployeeDepartmentRepository employeeDepartmentRepository;

    /**
     * 사원 부서 매핑 저장
     */
    public void saveEmployeeDepartment ( Department department, Employee employee ) {
        EmployeeDepartment employeeDepartment = EmployeeDepartment.builder()
                .employee( employee )
                .department( department )
                .build();
        employeeDepartmentRepository.save( employeeDepartment );
        employeeDepartment.register();
    }

    public void changeEmployeeDepartment ( Long employeeDepartmentId, Department willChangeDepartment ) {
        EmployeeDepartment employeeDepartment = employeeDepartmentRepository.findById( employeeDepartmentId ).orElseThrow();
        employeeDepartment.moveDepartment( willChangeDepartment );
    }
}
