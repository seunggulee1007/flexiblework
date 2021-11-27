package com.secommon.separtners.modules.company.employeemanagement;

import com.secommon.separtners.infra.commons.BaseServiceAnnotation;
import com.secommon.separtners.modules.company.department.Department;
import com.secommon.separtners.modules.company.department.repository.DepartmentRepository;
import com.secommon.separtners.modules.company.employee.Employee;
import com.secommon.separtners.modules.company.employee.repository.EmployeeRepository;
import com.secommon.separtners.modules.company.employeedepartment.EmployeeDepartment;
import com.secommon.separtners.modules.company.employeemanagement.form.EmployeeForm;
import com.secommon.separtners.modules.company.employeedepartment.EmployeeDepartmentRepository;
import com.secommon.separtners.modules.company.employeedepartment.EmployeeDepartmentService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@BaseServiceAnnotation
@RequiredArgsConstructor
public class EmployeeManagementService {

    private final EmployeeManagementRepository employeeManagementRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeDepartmentService employeeDepartmentService;
    private final EmployeeDepartmentRepository employeeDepartmentRepository;

    /**
     * 사원 부서 등록
     */
    public List<Long> registerEmployeeToDepartment ( List<EmployeeForm> employeeFormList ) {
        List<Long> ids = new ArrayList<>();
        for ( EmployeeForm employeeForm: employeeFormList ) {
            LocalDate applyDate = employeeForm.isRightNow() ? LocalDate.now() : employeeForm.getApplyDate();
            Long employeeDepartmentId = employeeForm.getEmployeeDepartmentId();
            if( employeeDepartmentId != null ) {
                ids.add( changeEmployeeToDepartment( employeeForm, applyDate, employeeDepartmentId ));
            } else {
                ids.add( saveNewEmployeeDepartment( employeeForm, applyDate ) );
            }
        }
        return ids;
    }

    private Long changeEmployeeToDepartment ( EmployeeForm employeeForm, LocalDate applyDate, Long employeeDepartmentId ) {
        EmployeeDepartment employeeDepartment = employeeDepartmentRepository.findById( employeeDepartmentId ).orElseThrow();
        Department willChangeDepartment = departmentRepository.findById( employeeForm.getDepartmentId() ).orElseThrow();
        EmployeeManagement employeeManagement = saveEmployeeManagement(
                employeeDepartment.getDepartment(),
                willChangeDepartment,
                employeeDepartment.getEmployee(),
                applyDate
        );
        if( employeeForm.isRightNow() ) {
            employeeDepartmentService.changeEmployeeDepartment( employeeDepartmentId, willChangeDepartment );
        }
        return employeeManagement.getId();
    }

    private Long saveNewEmployeeDepartment ( EmployeeForm employeeForm, LocalDate applyDate ) {
        Department department = departmentRepository.findById( employeeForm.getDepartmentId() ).orElseThrow();
        Employee employee = employeeRepository.findById( employeeForm.getEmployeeId() ).orElseThrow();
        EmployeeManagement employeeManagement = saveEmployeeManagement(
                null,
                department,
                employee,
                applyDate
        );
        // 즉시 여부가 true 일 경우 바로 저장하는 로직
        if( employeeForm.isRightNow()) {
            employeeDepartmentService.saveEmployeeDepartment( department, employee );
        }
        return employeeManagement.getId();
    }

    /**
     * 사원 관리 저장
     */
    private EmployeeManagement saveEmployeeManagement ( Department originDepartment, Department willChangeDepartment, Employee employee, LocalDate applyDate ) {
        EmployeeManagement employeeManagement = EmployeeManagement.builder()
                .employee( employee )
                .applyDate( applyDate )
                .originDepartment( originDepartment )
                .willChangeDepartment( willChangeDepartment )
                .build();
        employeeManagementRepository.save( employeeManagement );
        return employeeManagement;
    }

}
