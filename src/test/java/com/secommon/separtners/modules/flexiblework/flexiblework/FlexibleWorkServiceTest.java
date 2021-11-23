package com.secommon.separtners.modules.flexiblework.flexiblework;

import com.secommon.separtners.infra.AbstractContainerBaseTest;
import com.secommon.separtners.infra.MockMvcTest;
import com.secommon.separtners.infra.security.WithMockJwtAuthentication;
import com.secommon.separtners.modules.flexiblework.flexiblework.dto.FlexibleWorkDto;
import com.secommon.separtners.modules.flexiblework.flexiblework.enums.DailyWorkTime;
import com.secommon.separtners.modules.flexiblework.flexiblework.enums.FlexibleWorkType;
import com.secommon.separtners.modules.flexiblework.flexiblework.enums.SettlementUnitPeriod;
import com.secommon.separtners.modules.flexiblework.flexiblework.enums.WorkDayOfWeek;
import com.secommon.separtners.modules.flexiblework.flexiblework.form.FlexibleWorkForm;
import com.secommon.separtners.modules.flexiblework.flexiblework.form.FlexibleWorkSearchForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MockMvcTest
class FlexibleWorkServiceTest extends AbstractContainerBaseTest {


    @Autowired
    private FlexibleWorkService flexibleWorkService;

    @Test
    @DisplayName("조회 조건으로 유연근무 유형 현황 조회 - 성공(데이터 없음)")
    @WithMockJwtAuthentication
    void searchFlexibleWorkBySearchForm_success_no_data  () throws Exception {
        // given
        FlexibleWorkSearchForm searchForm = FlexibleWorkSearchForm.builder()
                .flexibleWorkType( "WEEK_52" )
                .build();
        Pageable pageable = PageRequest.of(0, 10);
        // when
        Page<FlexibleWorkDto> flexibleWorkByPageable = flexibleWorkService.findFlexibleWorkByPageable( searchForm, pageable);

        // then

        assertEquals(0, flexibleWorkByPageable.getTotalPages() );

    }

    @Test
    @DisplayName("조회 조건으로 유연근무 유형 현황 조회 - 성공")
    @WithMockJwtAuthentication
    void searchFlexibleWorkBySearchForm_success  () throws Exception {
        FlexibleWorkForm form = FlexibleWorkForm.builder()
                .flexibleWorkType( FlexibleWorkType.WEEK_52 )
                .flexibleWorkName( "테스트" )
                .settlementUnitPeriod( SettlementUnitPeriod.TWO_WEEK )
                .workDayOfWeekSet( Set.of( WorkDayOfWeek.FRIDAY) )
                .dailyWorkTime( DailyWorkTime.EIGHT )
                .build();
        flexibleWorkService.saveNewFlexibleWork( form );
        // given
        FlexibleWorkSearchForm searchForm = FlexibleWorkSearchForm.builder()
                .flexibleWorkType( "WEEK_52" )
                .build();
        Pageable pageable = PageRequest.of(0, 10);
        // when
        Page<FlexibleWorkDto> flexibleWorkByPageable = flexibleWorkService.findFlexibleWorkByPageable( searchForm, pageable);

        // then
        FlexibleWorkDto flexibleWorkDto = flexibleWorkByPageable.getContent().get( 0 );
        assertEquals(1, flexibleWorkByPageable.getTotalPages() );
        assertEquals( FlexibleWorkType.WEEK_52.getTitle(), flexibleWorkDto.getFlexibleWorkType() );

    }


}