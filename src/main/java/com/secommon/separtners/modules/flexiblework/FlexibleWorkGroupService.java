package com.secommon.separtners.modules.flexiblework;

import com.secommon.separtners.infra.commons.BaseServiceAnnotation;
import com.secommon.separtners.modules.company.department.Department;
import com.secommon.separtners.modules.company.department.repository.DepartmentRepository;
import com.secommon.separtners.modules.company.employee.Employee;
import com.secommon.separtners.modules.company.employee.repository.EmployeeRepository;
import com.secommon.separtners.modules.flexiblework.form.FlexibleWorkGroupForm;
import com.secommon.separtners.modules.flexiblework.repository.FlexibleWorkGroupRepository;
import lombok.RequiredArgsConstructor;

@BaseServiceAnnotation
@RequiredArgsConstructor
public class FlexibleWorkGroupService {

    private final FlexibleWorkGroupRepository flexibleWorkGroupRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public Long saveFlexibleWorkGroup( FlexibleWorkGroupForm flexibleWorkGroupForm) {
        Long flexibleWorkGroupId = flexibleWorkGroupForm.getFlexibleWorkGroupId();
        if (flexibleWorkGroupId != null ) {
            FlexibleWorkGroup flexibleWorkGroup = flexibleWorkGroupRepository.findById( flexibleWorkGroupId ).orElseThrow();
            for ( Long departmentId: flexibleWorkGroupForm.getDepartmentIds() ) {
                Department department = departmentRepository.findById( departmentId ).orElseThrow();
                flexibleWorkGroup.addDepartment(department);
            }
            for ( Long employeeId: flexibleWorkGroupForm.getEmployeeIds() ) {
                Employee employee = employeeRepository.findById( employeeId ).orElseThrow();
                flexibleWorkGroup.addEmployee(employee);
            }
        } else {

        }
        return flexibleWorkGroupId;
    }

}
