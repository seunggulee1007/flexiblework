package com.secommon.separtners.modules.flexiblework.flexiblework.form;

import com.secommon.separtners.modules.flexiblework.flexiblework.enums.DailyWorkTime;
import com.secommon.separtners.modules.flexiblework.flexiblework.enums.FlexibleWorkType;
import com.secommon.separtners.modules.flexiblework.flexiblework.enums.SettlementUnitPeriod;
import com.secommon.separtners.modules.flexiblework.flexiblework.enums.WorkDayOfWeek;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class FlexibleWorkForm {

    @NotNull(message = "유연근무 유형을 선택해 주세요.")
    private FlexibleWorkType flexibleWorkType;

    @NotEmpty(message = "유연근무 명칭을 입력해 주세요.")
    private String flexibleWorkName;

    private Set<WorkDayOfWeek> workDayOfWeekSet;

    @NotNull(message = "1일 근로시간을 선택해 주세요.")
    private DailyWorkTime dailyWorkTime;

    @NotNull(message = "정산 단위 기간을 선택해 주세요.")
    private SettlementUnitPeriod settlementUnitPeriod;

    @NotNull(message = "적용 시작일자를 입력해 주세요.")
    private LocalDate applyDateFrom;

    private LocalDate applyDateTo;

    private LocalTime startTime;

    private LocalTime endTime;

    private boolean restExist;

    private boolean mandatoryTimeExist;

    /** 사용 여부 */
    private boolean active;

    /** 마감 여부 */
    private boolean close;

    @Builder.Default
    private List<RestTimeForm> restTimeList = new ArrayList<>();

    @Builder.Default
    private List<MandatoryTimeForm> mandatoryTimeList = new ArrayList<>();

}
