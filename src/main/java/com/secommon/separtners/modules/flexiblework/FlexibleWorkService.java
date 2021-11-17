package com.secommon.separtners.modules.flexiblework;

import com.secommon.separtners.infra.commons.BaseServiceAnnotation;
import com.secommon.separtners.modules.common.EnumMapperValue;
import com.secommon.separtners.modules.flexiblework.enums.DailyWorkTime;
import com.secommon.separtners.modules.flexiblework.enums.FlexibleWorkType;
import com.secommon.separtners.modules.flexiblework.enums.SettlementUnitPeriod;
import com.secommon.separtners.modules.flexiblework.enums.WorkDayOfWeek;
import com.secommon.separtners.modules.flexiblework.form.FlexibleWorkForm;
import com.secommon.separtners.modules.flexiblework.form.MandatoryTimeForm;
import com.secommon.separtners.modules.flexiblework.form.RestTimeForm;
import com.secommon.separtners.modules.flexiblework.repository.FlexibleWorkRepository;
import com.secommon.separtners.modules.flexiblework.repository.MandatoryTimeRepository;
import com.secommon.separtners.modules.flexiblework.repository.RestTimeRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@BaseServiceAnnotation
@RequiredArgsConstructor
public class FlexibleWorkService {

    private final FlexibleWorkRepository flexibleWorkRepository;
    private final RestTimeRepository restTimeRepository;
    private final MandatoryTimeRepository mandatoryTimeRepository;

    public Long saveNewFlexibleWork( FlexibleWorkForm flexibleWorkForm ) {
        FlexibleWork flexibleWork = saveFlexibleWork( flexibleWorkForm );
        saveMandatoryTime( flexibleWork, flexibleWorkForm );
        saveRestTime( flexibleWork, flexibleWorkForm );
        return flexibleWork.getId();
    }

    /**
     * 휴게 시간 저장
     */
    private void saveRestTime ( FlexibleWork flexibleWork, FlexibleWorkForm flexibleWorkForm ) {
        if( flexibleWorkForm.isRestExist() ) {
            List<RestTimeForm> restTimeList = flexibleWorkForm.getRestTimeFormList();
            List<RestTime> restTimes = new ArrayList<>();
            for ( RestTimeForm restTimeForm: restTimeList ) {
                RestTime restTime;
                if( restTimeForm.getRestTimeId() != null ) {
                    restTime = restTimeRepository.findById( restTimeForm.getRestTimeId() ).orElseThrow();
                    restTime.changeTimes(restTimeForm);
                } else {
                    restTime = RestTime.builder()
                            .startTime( restTimeForm.getStartTime() )
                            .endTime( restTimeForm.getEndTime() )
                            .build();
                }
                restTime.setFlexibleWork(flexibleWork);
                restTimes.add( restTime );
            }
            restTimeRepository.saveAll( restTimes );
        }
    }

    /**
     * 의무 시간 저장
     */
    private void saveMandatoryTime ( FlexibleWork flexibleWork, FlexibleWorkForm flexibleWorkForm ) {
        if( flexibleWork.isMandatoryTimeExist() ) {
            List<MandatoryTimeForm> mandatoryTimeList = flexibleWorkForm.getMandatoryTimeFormList();
            List<MandatoryTime> mandatoryTimes = new ArrayList<>();
            for ( MandatoryTimeForm mandatoryTimeForm: mandatoryTimeList ) {
                MandatoryTime mandatoryTime = MandatoryTime.builder()
                        .startTime( mandatoryTimeForm.getStartTime() )
                        .endTime( mandatoryTimeForm.getEndTime() )
                        .build();
                mandatoryTime.setFlexibleWork( flexibleWork );
                mandatoryTimes.add( mandatoryTime );
            }
            mandatoryTimeRepository.saveAll( mandatoryTimes );
        }
    }

    private FlexibleWork saveFlexibleWork ( FlexibleWorkForm flexibleWorkForm ) {
        FlexibleWork flexibleWork = FlexibleWork.builder()
                .flexibleWorkType( flexibleWorkForm.getFlexibleWorkType() )
                .flexibleWorkName( flexibleWorkForm.getFlexibleWorkName() )
                .workDayOfWeeks( flexibleWorkForm.getWorkDayOfWeekSet() )
                .settlementUnitPeriod( flexibleWorkForm.getSettlementUnitPeriod() )
                .applyDateFrom( flexibleWorkForm.getApplyDateFrom() )
                .applyDateTo( flexibleWorkForm.getApplyDateTo() )
                .restExist( flexibleWorkForm.isRestExist() )
                .mandatoryTimeExist( flexibleWorkForm.isMandatoryTimeExist() )
            .build();
        flexibleWork.setApplyDate();
        flexibleWorkRepository.save( flexibleWork );
        return flexibleWork;
    }

    public FlexibleCodeDto getFlexibleCodeList() {
        return FlexibleCodeDto.builder()
                        .flexibleWorkTypeList(
                            Arrays.stream( FlexibleWorkType.values() )
                                    .map( EnumMapperValue::new )
                                    .collect( Collectors.toList() )
                        )
                        .dailyWorkTimeList(
                                Arrays.stream( DailyWorkTime.values() )
                                        .map( EnumMapperValue::new )
                                        .collect( Collectors.toList() )
                        )
                        .settlementUnitPeriodList(
                                Arrays.stream( SettlementUnitPeriod.values() )
                                        .map( EnumMapperValue::new )
                                        .collect( Collectors.toList() )
                        )
                        .workDayOfWeekList(
                                Arrays.stream( WorkDayOfWeek.values() )
                                        .map( EnumMapperValue::new )
                                        .collect( Collectors.toList() )
                        )
                .build();
    }


}
