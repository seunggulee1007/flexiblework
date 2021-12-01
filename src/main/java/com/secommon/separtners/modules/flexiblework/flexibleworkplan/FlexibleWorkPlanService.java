package com.secommon.separtners.modules.flexiblework.flexibleworkplan;

import com.secommon.separtners.infra.commons.BaseServiceAnnotation;
import com.secommon.separtners.modules.company.employee.dto.EmployeeWorkDto;
import com.secommon.separtners.modules.company.employee.repository.EmployeeRepository;
import com.secommon.separtners.modules.flexiblework.calculateworkplan.CalculateWorkPlanService;
import com.secommon.separtners.modules.flexiblework.flexibleworkplan.form.FlexibleWorkPlanForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@BaseServiceAnnotation
@RequiredArgsConstructor
public class FlexibleWorkPlanService {

    private final FlexibleWorkPlanRepository flexibleWorkPlanRepository;

    private final EmployeeRepository employeeRepository;

    private final CalculateWorkPlanService calculateWorkPlanService;


    public int saveFlexibleWorkPlan(FlexibleWorkPlanForm flexibleWorkPlanForm) {
        List<FlexibleWorkPlan> flexibleWorkPlans = calculateWorkPlanService.calculateWorkPlan(flexibleWorkPlanForm);
        flexibleWorkPlanRepository.saveAll(flexibleWorkPlans);
        return flexibleWorkPlans.size();
    }

    public EmployeeWorkDto findFlexibleWorkPlanByEmployee(Long employeeId) {
        return employeeRepository.findAllWithWorkGroupAndWorkAndTimes(employeeId);
    }

}
