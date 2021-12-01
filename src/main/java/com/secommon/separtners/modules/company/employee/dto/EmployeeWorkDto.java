package com.secommon.separtners.modules.company.employee.dto;

import com.secommon.separtners.modules.account.Account;
import com.secommon.separtners.modules.company.employee.Employee;
import com.secommon.separtners.modules.flexiblework.flexiblework.FlexibleWork;
import com.secommon.separtners.modules.flexiblework.flexiblework.form.MandatoryTimeForm;
import com.secommon.separtners.modules.flexiblework.flexiblework.form.RestTimeForm;
import com.secommon.separtners.modules.flexiblework.flexibleworkgroup.FlexibleWorkGroup;
import com.secommon.separtners.modules.flexiblework.flexibleworkplan.dto.FlexibleWorkPlanDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class EmployeeWorkDto {

    private Long employeeId;

    private Long accountId;

    private Long flexibleWorkId;

    private Long flexibleWorkGroupId;

    private LocalTime startTime;

    private LocalTime endTime;

    private List<MandatoryTimeForm> mandatoryTimeList;

    private List<RestTimeForm> restTimeList;

    private List<FlexibleWorkPlanDto> flexibleWorkPlanList;

    public EmployeeWorkDto(Employee employee, Account account, FlexibleWork flexibleWork, FlexibleWorkGroup flexibleWorkGroup) {
        log.error("employeeWorkDto.getEmployeeId :: {} ", employee.getId());
        this.employeeId = employee.getId();
        this.accountId = account.getId();
        this.flexibleWorkId = flexibleWork.getId();
        this.flexibleWorkGroupId = flexibleWorkGroup.getId();
        this.startTime = flexibleWork.getStartTime();
        this.endTime = flexibleWork.getEndTime();
        this.mandatoryTimeList = flexibleWork.getMandatoryTimeList().stream().map(MandatoryTimeForm::new).collect(Collectors.toList());
        this.restTimeList = flexibleWork.getRestTimeList().stream().map(RestTimeForm::new).collect(Collectors.toList());
        this.flexibleWorkPlanList = employee.getFlexibleWorkPlanList().stream().map(FlexibleWorkPlanDto::new).collect(Collectors.toList());
    }

}
