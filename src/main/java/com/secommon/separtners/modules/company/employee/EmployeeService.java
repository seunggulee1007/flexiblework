package com.secommon.separtners.modules.company.employee;

import com.secommon.separtners.infra.commons.BaseServiceAnnotation;
import com.secommon.separtners.modules.company.department.repository.DepartmentRepository;
import com.secommon.separtners.modules.company.employee.form.PositionForm;
import com.secommon.separtners.modules.company.employee.form.StatusForm;
import com.secommon.separtners.modules.company.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

@BaseServiceAnnotation
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public Position updatePosition ( PositionForm positionForm ) {
        Employee employee = getEmployee( positionForm.getEmployeeId() );
        employee.updatePosition( positionForm.getPosition());
        return employee.getPosition();
    }

    public EmployeeStatus updateStatus ( StatusForm statusForm ) {
        Employee employee = getEmployee( statusForm.getEmployeeId() );
        employee.updateStatus( statusForm.getEmployeeStatus() );
        return employee.getStatus();
    }

    private Employee getEmployee ( Long employeeId ) {
        return employeeRepository.findById( employeeId ).orElseThrow();
    }

}
