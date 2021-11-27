package com.secommon.separtners.modules.flexiblework.flexiblework;

import com.secommon.separtners.infra.AbstractContainerBaseTest;
import com.secommon.separtners.infra.MockMvcTest;
import com.secommon.separtners.infra.security.WithMockJwtAuthentication;
import com.secommon.separtners.modules.flexiblework.flexiblework.enums.DailyWorkTime;
import com.secommon.separtners.modules.flexiblework.flexiblework.enums.FlexibleWorkType;
import com.secommon.separtners.modules.flexiblework.flexiblework.enums.SettlementUnitPeriod;
import com.secommon.separtners.modules.flexiblework.flexiblework.enums.WorkDayOfWeek;
import com.secommon.separtners.modules.flexiblework.flexiblework.form.FlexibleWorkForm;
import com.secommon.separtners.modules.flexiblework.flexiblework.repository.FlexibleWorkRepository;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
class FlexibleWorkControllerTest extends AbstractContainerBaseTest {


    @Autowired
    private FlexibleWorkRepository flexibleWorkRepository;

    @Test
    @DisplayName("등록 테스트")
    @WithMockJwtAuthentication
    @Order( 1 )
    void registerFlexibleWork () throws Exception {
        // given
        FlexibleWorkForm form = FlexibleWorkForm.builder()
                .flexibleWorkType( FlexibleWorkType.WEEK_52 )
                .flexibleWorkName( "테스트" )
                .settlementUnitPeriod( SettlementUnitPeriod.TWO_WEEK )
                .workDayOfWeekSet( Set.of( WorkDayOfWeek.FRIDAY) )
                .dailyWorkTime( DailyWorkTime.EIGHT )
                .startTime( LocalTime.of( 9, 0 ) )
                .endTime( LocalTime.of( 18,0 ) )
                .applyDateFrom( LocalDate.now() )
                .build();
        // when
        this.mockMvc.perform( post("/api/flexible-work")
                .contentType( MediaType.APPLICATION_JSON )
                .content( this.objectMapper.writeValueAsString( form) )
        )
                .andDo( print() )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "success" ).value( true ) )
                .andExpect( jsonPath( "response" ).isNotEmpty() )
                .andExpect( jsonPath( "response" ).isNumber() )
                ;

        Optional<FlexibleWork> byId = flexibleWorkRepository.findById( 1L );
        // then
        assertNotNull( byId );

    }
}