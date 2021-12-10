package com.secommon.separtners.modules.flexiblework.flexiblework;

import com.secommon.separtners.infra.commons.BaseServiceAnnotation;
import com.secommon.separtners.modules.common.EnumMapperValue;
import com.secommon.separtners.modules.flexiblework.flexiblework.dto.FlexibleWorkDto;
import com.secommon.separtners.modules.flexiblework.flexiblework.enums.DailyWorkTime;
import com.secommon.separtners.modules.flexiblework.flexiblework.enums.FlexibleWorkType;
import com.secommon.separtners.modules.flexiblework.flexiblework.enums.SettlementUnitPeriod;
import com.secommon.separtners.modules.flexiblework.flexiblework.enums.WorkDayOfWeek;
import com.secommon.separtners.modules.flexiblework.flexiblework.form.FlexibleWorkForm;
import com.secommon.separtners.modules.flexiblework.flexiblework.form.FlexibleWorkSearchForm;
import com.secommon.separtners.modules.flexiblework.flexiblework.form.MandatoryTimeForm;
import com.secommon.separtners.modules.flexiblework.flexiblework.repository.FlexibleWorkRepository;
import com.secommon.separtners.modules.flexiblework.flexiblework.repository.MandatoryTimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@BaseServiceAnnotation
@RequiredArgsConstructor
public class FlexibleWorkService {

    private final FlexibleWorkRepository flexibleWorkRepository;
    private final MandatoryTimeRepository mandatoryTimeRepository;

    /**
     * 유연근무 유형 조회
     * @param searchForm : 검색 조건
     * @param pageable : 페이징 조건
     */
    public Page<FlexibleWorkDto> findFlexibleWorkByPageable( FlexibleWorkSearchForm searchForm, Pageable pageable) {
        return flexibleWorkRepository.findAllBySearchForm( searchForm, pageable );
    }

    /**
     * 저장 프로세스
     * @param flexibleWorkForm : 유연근무 폼
     */
    public Long saveNewFlexibleWork( FlexibleWorkForm flexibleWorkForm ) {
        FlexibleWork flexibleWork = saveFlexibleWork( flexibleWorkForm );
        saveMandatoryTime( flexibleWork, flexibleWorkForm );
        return flexibleWork.getId();
    }

    /**
     * 의무 시간 저장
     */
    private void saveMandatoryTime ( FlexibleWork flexibleWork, FlexibleWorkForm flexibleWorkForm ) {
        if( flexibleWork.isMandatoryTimeExist() ) {
            List<MandatoryTimeForm> mandatoryTimeList = flexibleWorkForm.getMandatoryTimeList();
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

    /**
     * 유연근무 저장
     * @param flexibleWorkForm : 유연근무 폼
     */
    private FlexibleWork saveFlexibleWork ( FlexibleWorkForm flexibleWorkForm ) {
        FlexibleWork flexibleWork = FlexibleWork.builder()
                .flexibleWorkType( flexibleWorkForm.getFlexibleWorkType() )
                .flexibleWorkName( flexibleWorkForm.getFlexibleWorkName() )
                .workDayOfWeeks( flexibleWorkForm.getWorkDayOfWeekSet() )
                .settlementUnitPeriod( flexibleWorkForm.getSettlementUnitPeriod() )
                .dailyWorkTime( flexibleWorkForm.getDailyWorkTime() )
                .applyDateFrom( flexibleWorkForm.getApplyDateFrom() )
                .applyDateTo( flexibleWorkForm.getApplyDateTo() )
                .restExist( flexibleWorkForm.isRestExist() )
                .startTime( flexibleWorkForm.getStartTime() )
                .endTime( flexibleWorkForm.getEndTime() )
                .mandatoryTimeExist( flexibleWorkForm.isMandatoryTimeExist() )
            .build();
        flexibleWork.setApplyDate();
        flexibleWorkRepository.save( flexibleWork );
        return flexibleWork;
    }

    /**
     * enum 을 코드로 변환
     */
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
