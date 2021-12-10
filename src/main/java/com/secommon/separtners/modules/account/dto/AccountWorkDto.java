package com.secommon.separtners.modules.account.dto;

import com.secommon.separtners.modules.account.Account;
import com.secommon.separtners.modules.flexiblework.flexiblework.FlexibleWork;
import com.secommon.separtners.modules.flexiblework.flexiblework.form.MandatoryTimeForm;
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
public class AccountWorkDto {

    private Long accountId;

    private Long flexibleWorkId;

    private LocalTime startTime;

    private LocalTime endTime;

    private List<MandatoryTimeForm> mandatoryTimeList;

    private int restTime;

    private List<FlexibleWorkPlanDto> flexibleWorkPlanList;

    public AccountWorkDto(Account account, FlexibleWork flexibleWork) {
        this.accountId = account.getId();
        this.flexibleWorkId = flexibleWork.getId();
        this.startTime = flexibleWork.getStartTime();
        this.endTime = flexibleWork.getEndTime();
        this.mandatoryTimeList = flexibleWork.getMandatoryTimeList().stream().map(MandatoryTimeForm::new).collect(Collectors.toList());
        this.restTime = flexibleWork.getRestTime();
        this.flexibleWorkPlanList = account.getFlexibleWorkPlanList().stream().map(FlexibleWorkPlanDto::new).collect(Collectors.toList());
    }

}
