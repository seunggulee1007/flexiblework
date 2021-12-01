package com.secommon.separtners.modules.flexiblework.flexibleworkplan.dto;

import com.secommon.separtners.modules.flexiblework.flexibleworkplan.FlexibleWorkPlan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.beans.BeanUtils.copyProperties;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class FlexibleWorkPlanDto {

    /** 계획 식별번호 */
    private Long flexibleWorkPlanId;

    /** 계획 년도 */
    private String planYear;

    /** 계획 월 */
    private String planMonth;

    /** 계획 일 */
    private String planDay;

    /** 계획일자 */
    private LocalDate planDate;

    /** 시작 시간 */
    private LocalTime startTime;

    /** 종료 시간 */
    private LocalTime endTime;

    /** 사원 아이디 */
    private Long employeeId;

    public FlexibleWorkPlanDto(FlexibleWorkPlan flexibleWorkPlan) {
        copyProperties(flexibleWorkPlan, this);
        this.flexibleWorkPlanId = flexibleWorkPlan.getId();
        this.employeeId = flexibleWorkPlan.getEmployee().getId();
    }

}
