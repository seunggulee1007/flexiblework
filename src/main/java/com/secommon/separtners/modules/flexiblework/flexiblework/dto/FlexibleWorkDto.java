package com.secommon.separtners.modules.flexiblework.flexiblework.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.secommon.separtners.modules.flexiblework.flexiblework.FlexibleWork;
import com.secommon.separtners.modules.flexiblework.flexiblework.enums.WorkDayOfWeek;
import com.secommon.separtners.modules.flexiblework.flexiblework.form.MandatoryTimeForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class FlexibleWorkDto {

    /** 근무 유형 아이디 */
    private Long flexibleWorkId;

    /** 근무유형 타입 */
    private String flexibleWorkType;

    /** 유연근무 명칭 */
    private String flexibleWorkName;

    /** 1일 근로 시간 */
    private String dailyWorkTime;

    /** 근무 요일 */
    private String workDayOfWeek;

    /** 정산단위 기간 */
    private String settlementUnitPeriod;

    /** 휴게시간 유무 */
    private boolean restExist;

    /** 의무 시간 유무 */
    private boolean mandatoryTimeExist;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime endTime;

    /** 적용기간 from */
    private LocalDate applyDateFrom;

    /** 적용기간 to */
    private LocalDate applyDateTo;

    /** 사용 여부 */
    private boolean active;

    /** 근무자 수 */
    private Long workPersonCnt;

    /** 소속 부서 숫자 */
    private Long workDepartmentCnt;

    /** 휴게 시간 */
    private int restTime;

    /** 의무 시간 */
    private List<MandatoryTimeForm> mandatoryTimeList = new ArrayList<>();

    public FlexibleWorkDto(FlexibleWork flexibleWork) {
        copyProperties(flexibleWork, this);
        this.flexibleWorkType = flexibleWork.getFlexibleWorkType().getTitle();
        this.workDayOfWeek = String.join( ",", flexibleWork.getWorkDayOfWeeks().stream().map( WorkDayOfWeek::getTitle ).collect( Collectors.toSet()) );
        this.dailyWorkTime = flexibleWork.getDailyWorkTime().getTitle();
        this.settlementUnitPeriod = flexibleWork.getSettlementUnitPeriod().getTitle();
        this.workPersonCnt = !flexibleWork.getFlexibleWorkGroupList().isEmpty() ? flexibleWork.getFlexibleWorkGroupList().stream().filter(flexibleWorkGroup -> flexibleWorkGroup.getAccount() != null).count() : 0;
        this.workDepartmentCnt = !flexibleWork.getFlexibleWorkGroupList().isEmpty() ? flexibleWork.getFlexibleWorkGroupList().stream().filter(flexibleWorkGroup -> flexibleWorkGroup.getDepartment() != null).count() : 0;
        this.restTime = flexibleWork.isRestExist() ? flexibleWork.getRestTime(): 0;
        this.mandatoryTimeList = flexibleWork.isMandatoryTimeExist() ? flexibleWork.getMandatoryTimeList().stream().map( MandatoryTimeForm::new ).collect( Collectors.toList()) : new ArrayList<>();
        this.active = flexibleWork.isActive();
    }

}
