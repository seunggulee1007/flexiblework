package com.secommon.separtners.modules.flexiblework.validator;


import com.secommon.separtners.modules.flexiblework.form.FlexibleWorkForm;
import com.secommon.separtners.modules.flexiblework.form.MandatoryTimeForm;
import com.secommon.separtners.modules.flexiblework.form.RestTimeForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalTime;
import java.util.List;

@Slf4j
@Component
public class FlexibleWorkFormValidator implements Validator {

    @Override
    public boolean supports ( @NonNull Class<?> clazz ) {
        return FlexibleWorkForm.class.isAssignableFrom( clazz );
    }

    @Override
    public void validate ( @NonNull Object target, @NonNull Errors errors ) {
        FlexibleWorkForm flexibleWorkForm = ( FlexibleWorkForm ) target;
        log.error( "FlexibleWorkForm:: {}", flexibleWorkForm );
        List<MandatoryTimeForm> mandatoryTimeFormList = flexibleWorkForm.getMandatoryTimeList();
        List<RestTimeForm> restTimeFormList = flexibleWorkForm.getRestTimeList();
        if( flexibleWorkForm.isRestExist() && restTimeFormList.isEmpty() ) {
            errors.rejectValue( "restTimeList", "wrong.value", "휴게시간이 있는 경우 휴게시간이 입력되어야 합니다." );
        } else if ( flexibleWorkForm.isMandatoryTimeExist() && mandatoryTimeFormList.isEmpty()) {
            errors.rejectValue( "mandatoryTimeList", "wrong.value", "의무시간이 있는 경우 의무시간이 입력되어야 합니다." );
        } else if ( flexibleWorkForm.getWorkDayOfWeekSet().isEmpty() ) {
            errors.rejectValue( "workDayOfWeekSet", "wrong.value", "적용 요일을 하나 이상 선택하셔야 합니다." );
        } else if ( flexibleWorkForm.getApplyDateTo() != null && flexibleWorkForm.getApplyDateTo().isBefore( flexibleWorkForm.getApplyDateFrom() )) {
            errors.rejectValue( "applyDateTo", "wrong.value", "적용 종료일자가 적용 시작일자보다 이전 날짜일 수 없습니다." );
        }
        validationMandatoryTime( errors, mandatoryTimeFormList );
        validationRestTime( errors, restTimeFormList );
    }

    private void validationRestTime ( Errors errors, List<RestTimeForm> restTimeFormList ) {
        int nextCount = 1;
        e: for ( int i = 0; i < restTimeFormList.size(); i++ ) {
            RestTimeForm restTimeForm = restTimeFormList.get( i );
            for ( int j = nextCount; j < restTimeFormList.size(); j++) {
                RestTimeForm subRestTimeForm = restTimeFormList.get( j );
                if( isContainMandatoryTime( restTimeForm.getStartTime(), subRestTimeForm.getStartTime(), subRestTimeForm.getEndTime() ) ||
                        isContainMandatoryTime( restTimeForm.getEndTime(), subRestTimeForm.getStartTime(), subRestTimeForm.getEndTime() ) ||
                        isContainAllTime(restTimeForm.getStartTime(), restTimeForm.getEndTime(), subRestTimeForm.getStartTime(), subRestTimeForm.getEndTime())
                ) {
                    errors.rejectValue( "restTimeFormList", "wrong.value", "의무시간은 시간이 겹칠 수 없습니다." );
                    break e;
                }
                nextCount++;
            }
        }
    }

    private void validationMandatoryTime ( Errors errors, List<MandatoryTimeForm> mandatoryTimeFormList ) {
        int nextCount = 1;
        e: for ( int i = 0; i < mandatoryTimeFormList.size(); i++ ) {
            MandatoryTimeForm mandatoryTime = mandatoryTimeFormList.get( i );
            for ( int j = nextCount; j < mandatoryTimeFormList.size(); j++) {
                MandatoryTimeForm subMandatoryTime = mandatoryTimeFormList.get( j );
                if( isContainMandatoryTime( mandatoryTime.getStartTime(), subMandatoryTime.getStartTime(), subMandatoryTime.getEndTime() ) ||
                    isContainMandatoryTime( mandatoryTime.getEndTime(), subMandatoryTime.getStartTime(), subMandatoryTime.getEndTime() ) ||
                        isContainAllTime(mandatoryTime.getStartTime(), mandatoryTime.getEndTime(), subMandatoryTime.getStartTime(), subMandatoryTime.getEndTime())
                ) {
                    errors.rejectValue( "restTimeFormList", "wrong.value", "의무시간은 시간이 겹칠 수 없습니다." );
                    break e;
                }
                nextCount++;
            }
        }
    }

    private boolean isContainMandatoryTime ( LocalTime localTime, LocalTime startTime, LocalTime endTime ) {
        return localTime.isAfter( startTime ) && localTime.isBefore( endTime );
    }

    private boolean isContainAllTime (
            LocalTime startTime1, LocalTime endTime1,
            LocalTime startTime2, LocalTime endTime2
    ) {
        return startTime1.isAfter(startTime2) && endTime1.isBefore(endTime2);
    }

}
