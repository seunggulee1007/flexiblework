package com.secommon.separtners.modules.flexiblework.flexibleworkplan.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class FlexibleWorkPlanTimeForm {
    /** 적용 일자 */
    private LocalDate planDate;

    /** 시작 시간 */
    private LocalTime startTime;

    /** 종료 시간 */
    private LocalTime endTime;

}
