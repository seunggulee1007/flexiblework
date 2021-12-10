package com.secommon.separtners.modules.company.employee;

import com.secommon.separtners.infra.commons.BaseServiceAnnotation;
import com.secommon.separtners.modules.company.employee.dto.EmployeeDto;
import com.secommon.separtners.modules.company.employee.enums.EmployeeStatus;
import com.secommon.separtners.modules.company.employee.enums.Position;
import com.secommon.separtners.modules.company.employee.form.EmployeeSearchForm;
import com.secommon.separtners.modules.company.employee.form.PositionForm;
import com.secommon.separtners.modules.company.employee.form.StatusForm;
import com.secommon.separtners.modules.company.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@BaseServiceAnnotation
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Position updatePosition (PositionForm positionForm ) {
        Employee employee = getEmployee( positionForm.getEmployeeId() );
        employee.updatePosition( positionForm.getPosition());
        return employee.getPosition();
    }

    public EmployeeStatus updateStatus (StatusForm statusForm ) {
        Employee employee = getEmployee( statusForm.getEmployeeId() );
        employee.updateStatus( statusForm.getEmployeeStatus() );
        return employee.getStatus();
    }

    private Employee getEmployee ( Long employeeId ) {
        return employeeRepository.findById( employeeId ).orElseThrow();
    }

    public Page<EmployeeDto> getEmployeeListNotRegistered(Long departmentId, EmployeeSearchForm condition, Pageable pageable) {
        return employeeRepository.findAllNotRegistered(departmentId, condition, pageable);
    }
}
