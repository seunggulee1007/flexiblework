package com.secommon.separtners.modules.flexiblework.calculateworkplan;

import com.secommon.separtners.infra.advice.exceptions.BadRequestException;
import com.secommon.separtners.infra.commons.BaseServiceAnnotation;
import com.secommon.separtners.modules.company.employee.Employee;
import com.secommon.separtners.modules.company.employee.repository.EmployeeRepository;
import com.secommon.separtners.modules.flexiblework.flexiblework.FlexibleWork;
import com.secommon.separtners.modules.flexiblework.flexiblework.MandatoryTime;
import com.secommon.separtners.modules.flexiblework.flexibleworkgroup.FlexibleWorkGroup;
import com.secommon.separtners.modules.flexiblework.flexibleworkplan.FlexibleWorkPlan;
import com.secommon.separtners.modules.flexiblework.flexibleworkplan.form.FlexibleWorkPlanForm;
import com.secommon.separtners.modules.flexiblework.flexibleworkplan.form.FlexibleWorkPlanTimeForm;
import com.secommon.separtners.utils.TimeUtil;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@BaseServiceAnnotation
@RequiredArgsConstructor
public class CalculateWorkPlanServiceImpl implements CalculateWorkPlanService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<FlexibleWorkPlan> calculateWorkPlan(FlexibleWorkPlanForm flexibleWorkPlanForm) {

        Employee employee = employeeRepository.findById(flexibleWorkPlanForm.getEmployeeId()).orElseThrow();
        List<MandatoryTime> mandatoryTimeList = getMandatoryTimes(employee);
        List<FlexibleWorkPlan> flexibleWorkPlanList = new ArrayList<>();
        for (FlexibleWorkPlanTimeForm flexibleWorkPlanTimeForm : flexibleWorkPlanForm.getFlexibleWorkPlanTimeFormList()) {
            // 의무 시간이 있다면
            checkMandatoryTime(mandatoryTimeList, flexibleWorkPlanTimeForm);

            FlexibleWorkPlan flexibleWorkPlan = makeFlexibleWorkPlan(employee, flexibleWorkPlanTimeForm);
            flexibleWorkPlanList.add(flexibleWorkPlan);
        }
        return flexibleWorkPlanList;
    }

    private FlexibleWorkPlan makeFlexibleWorkPlan(Employee employee, FlexibleWorkPlanTimeForm flexibleWorkPlanTimeForm) {
        FlexibleWorkPlan flexibleWorkPlan = FlexibleWorkPlan.builder()
                .planDate(flexibleWorkPlanTimeForm.getPlanDate())
                .startTime(flexibleWorkPlanTimeForm.getStartTime())
                .endTime(flexibleWorkPlanTimeForm.getEndTime())
                .employee(employee)
                .build();
        flexibleWorkPlan.calcDate();
        return flexibleWorkPlan;
    }

    private void checkMandatoryTime(List<MandatoryTime> mandatoryTimeList, FlexibleWorkPlanTimeForm flexibleWorkPlanTimeForm) {
        if(!mandatoryTimeList.isEmpty()) {
            for (MandatoryTime mandatoryTime : mandatoryTimeList) {
                TimeUtil timeUtil = TimeUtil.of(mandatoryTime.getStartTime(), mandatoryTime.getEndTime());
                if(!timeUtil.isContains(flexibleWorkPlanTimeForm.getStartTime(), flexibleWorkPlanTimeForm.getEndTime())) {
                    throw new BadRequestException("의무시간은 반드시 포함되어야 합니다. 의무시간은 " + mandatoryTime.getStartTime() + " ~ " + mandatoryTime.getEndTime() + "입니다.");
                }
            }
        }
    }

    private List<MandatoryTime> getMandatoryTimes(Employee employee) {
        FlexibleWorkGroup flexibleWorkGroup = employee.getFlexibleWorkGroup();
        FlexibleWork flexibleWork = flexibleWorkGroup.getFlexibleWork();
        return flexibleWork.getMandatoryTimeList();
    }
}
