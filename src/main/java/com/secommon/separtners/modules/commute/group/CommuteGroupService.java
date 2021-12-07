package com.secommon.separtners.modules.commute.group;

import com.secommon.separtners.infra.commons.BaseServiceAnnotation;
import com.secommon.separtners.modules.commute.group.form.CommuteGroupForm;
import com.secommon.separtners.modules.company.employee.Employee;
import com.secommon.separtners.modules.company.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@BaseServiceAnnotation
@RequiredArgsConstructor
public class CommuteGroupService {

    private final CommuteGroupRepository commuteGroupRepository;
    private final EmployeeRepository employeeRepository;

    public void saveCommuteGroup(CommuteGroupForm commuteGroupForm) {
        CommuteGroup commuteGroup = CommuteGroup.builder()
                .groupName(commuteGroupForm.getGroupName())
                .active(commuteGroupForm.isActive())
            .build();
        commuteGroupRepository.save(commuteGroup);
        List<Long> employeeIds = commuteGroupForm.getEmployeeIdList();
        for (Long employeeId : employeeIds) {
            Employee employee = employeeRepository.findById(employeeId).orElseThrow();
            employee.setCommuteGroup(commuteGroup);
        }
    }

}
