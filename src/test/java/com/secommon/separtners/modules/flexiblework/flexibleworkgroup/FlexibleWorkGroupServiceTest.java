package com.secommon.separtners.modules.flexiblework.flexibleworkgroup;

import com.secommon.separtners.infra.AbstractContainerBaseTest;
import com.secommon.separtners.infra.MockMvcTest;
import com.secommon.separtners.infra.security.WithMockJwtAuthentication;
import com.secommon.separtners.modules.company.employee.Employee;
import com.secommon.separtners.modules.company.employee.repository.EmployeeRepository;
import com.secommon.separtners.modules.flexiblework.flexiblework.FlexibleWork;
import com.secommon.separtners.modules.flexiblework.flexiblework.RestTime;
import com.secommon.separtners.modules.flexiblework.flexiblework.enums.DailyWorkTime;
import com.secommon.separtners.modules.flexiblework.flexiblework.enums.FlexibleWorkType;
import com.secommon.separtners.modules.flexiblework.flexiblework.enums.SettlementUnitPeriod;
import com.secommon.separtners.modules.flexiblework.flexiblework.enums.WorkDayOfWeek;
import com.secommon.separtners.modules.flexiblework.flexiblework.repository.FlexibleWorkRepository;
import com.secommon.separtners.modules.flexiblework.flexiblework.repository.RestTimeRepository;
import com.secommon.separtners.modules.flexiblework.flexibleworkgroup.form.FlexibleWorkGroupForm;
import com.secommon.separtners.modules.flexiblework.flexibleworkgroup.repository.FlexibleWorkGroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertNotEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@MockMvcTest
class FlexibleWorkGroupServiceTest extends AbstractContainerBaseTest {

    @Autowired
    private FlexibleWorkRepository flexibleWorkRepository;
    @Autowired
    private RestTimeRepository restTimeRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private FlexibleWorkGroupService flexibleWorkGroupService;
    @Autowired
    private FlexibleWorkGroupRepository flexibleWorkGroupRepository;

    @Test
    @DisplayName("유연근무 그룹 등록 테스트 - 성공")
    @WithMockJwtAuthentication
    @Order( 1 )
    void  flexibleWorkGroupRegister_success () throws Exception {
        // given
        FlexibleWork flexibleWork = saveFlexibleWork();
        String workGroupName = "테스트 그룹";
        Long workGroupId = saveWorkGroup( flexibleWork, workGroupName );
        // when
        FlexibleWorkGroup flexibleWorkGroup = flexibleWorkGroupRepository.findById( workGroupId ).orElseThrow();
        // then
        assertNotEmpty(flexibleWorkGroup.getEmployeeList(), "사원이 있네요");
        assertEquals(workGroupName, flexibleWorkGroup.getFlexibleWorkGroupName());
        assertEquals( 1, flexibleWorkGroup.getEmployeeList().size() );

    }

    private Long saveWorkGroup ( FlexibleWork flexibleWork, String workGroupName ) {
        Employee employee = Employee.builder().build();
        employeeRepository.save( employee );
        List<Long> employeeIds = new ArrayList<>();
        employeeIds.add( employee.getId() );
        FlexibleWorkGroupForm flexibleWorkGroupForm = FlexibleWorkGroupForm.builder()
                .flexibleWorkId( flexibleWork.getId() )
                .flexibleWorkGroupName( workGroupName )
                .employeeIds( employeeIds )
                .build();
        return flexibleWorkGroupService.saveFlexibleWorkGroup( flexibleWorkGroupForm );
    }


    @Test
    @DisplayName("그룹 수정")
    @Order( 2 )
    void modifyGroup () throws Exception {
        // given
        FlexibleWork flexibleWork = saveFlexibleWork();
        String workGroupName = "테스트 그룹";
        Long workGroupId = saveWorkGroup( flexibleWork, workGroupName );
        Employee employee = Employee.builder()
                .build();
        employeeRepository.save( employee );
        List<Long> employeeIds = new ArrayList<>();
        employeeIds.add( employee.getId() );
        String flexibleWorkGroupName = "변경된 이름??";
        FlexibleWorkGroupForm flexibleWorkGroupForm = FlexibleWorkGroupForm.builder()
                .flexibleWorkGroupId( workGroupId )
                .flexibleWorkId( flexibleWork.getId() )
                .flexibleWorkGroupName( flexibleWorkGroupName )
                .employeeIds( employeeIds )
                .build();
        // when
        Long flexibleWorkGroupId = flexibleWorkGroupService.saveFlexibleWorkGroup( flexibleWorkGroupForm );
        FlexibleWorkGroup savedFlexibleWorkGroup = flexibleWorkGroupRepository.findById( flexibleWorkGroupId ).orElseThrow();
        List<Employee> employeeList = savedFlexibleWorkGroup.getEmployeeList();
        // then
        assertEquals( flexibleWorkGroupName, savedFlexibleWorkGroup.getFlexibleWorkGroupName() );
        assertEquals( 1, savedFlexibleWorkGroup.getEmployeeList().size() );

    }


    private FlexibleWork saveFlexibleWork () {
        List<RestTime> restTimeList = new ArrayList<>();
        restTimeList.add( RestTime.builder()
                .startTime( LocalTime.of( 12,0 ) )
                .endTime( LocalTime.of( 13,0 ) )
                .build()
        );
        restTimeRepository.saveAll( restTimeList );
        FlexibleWork flexibleWork = FlexibleWork.builder()
                .flexibleWorkType( FlexibleWorkType.WEEK_52 )
                .flexibleWorkName( "테스트 그룹" )
                .dailyWorkTime( DailyWorkTime.EIGHT )
                .workDayOfWeeks( Set.of( WorkDayOfWeek.FRIDAY ) )
                .active( true )
                .applyDateFrom( LocalDate.now() )
                .startTime( LocalTime.of( 9,0 ) )
                .endTime( LocalTime.of( 18, 0 ) )
                .settlementUnitPeriod( SettlementUnitPeriod.ONE_MONTH )
                .restExist( true )
                .restTimeList( restTimeList )
                .build();
        flexibleWorkRepository.save( flexibleWork );
        return flexibleWork;
    }

}