package com.secommon.separtners.modules.flexiblework.flexibleworkplan;

import com.secommon.separtners.infra.security.WithMockJwtAuthentication;
import com.secommon.separtners.modules.account.Account;
import com.secommon.separtners.modules.account.repository.AccountRepository;
import com.secommon.separtners.modules.company.employee.dto.AccountWorkDto;
import com.secommon.separtners.modules.flexiblework.flexiblework.FlexibleWork;
import com.secommon.separtners.modules.flexiblework.flexiblework.MandatoryTime;
import com.secommon.separtners.modules.flexiblework.flexiblework.enums.DailyWorkTime;
import com.secommon.separtners.modules.flexiblework.flexiblework.enums.FlexibleWorkType;
import com.secommon.separtners.modules.flexiblework.flexiblework.enums.SettlementUnitPeriod;
import com.secommon.separtners.modules.flexiblework.flexiblework.enums.WorkDayOfWeek;
import com.secommon.separtners.modules.flexiblework.flexiblework.repository.FlexibleWorkRepository;
import com.secommon.separtners.modules.flexiblework.flexiblework.repository.MandatoryTimeRepository;
import com.secommon.separtners.modules.flexiblework.flexibleworkgroup.FlexibleWorkGroup;
import com.secommon.separtners.modules.flexiblework.flexibleworkgroup.repository.FlexibleWorkGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class FlexibleWorkPlanServiceTest {

    @Autowired
    private FlexibleWorkPlanService flexibleWorkPlanService;
    @Autowired
    private FlexibleWorkRepository flexibleWorkRepository;
    @Autowired
    private MandatoryTimeRepository mandatoryTimeRepository;
    @Autowired
    private FlexibleWorkGroupRepository flexibleWorkGroupRepository;
    @Autowired
    private AccountRepository accountRepository;


    @Test
    @DisplayName("조회 테스트")
    @WithMockJwtAuthentication
    void searchTest () throws Exception {
        // given
        FlexibleWork flexibleWork = saveFlexibleWork();
        saveMandatoryTime(flexibleWork);
        FlexibleWorkGroup flexibleWorkGroup = saveFlexibleWorkGroup(flexibleWork);
        Account account = accountRepository.findAll().get(0);
        account.setWorkGroup(flexibleWorkGroup);
        // when
        AccountWorkDto flexibleWorkPlanByEmployee = flexibleWorkPlanService.findFlexibleWorkPlanByAccount(account.getId());
        // then
        assertEquals(flexibleWorkPlanByEmployee.getAccountId(), account.getId());
        assertEquals(1, flexibleWorkPlanByEmployee.getMandatoryTimeList().size());

    }

    private FlexibleWorkGroup saveFlexibleWorkGroup(FlexibleWork flexibleWork) {
        FlexibleWorkGroup flexibleWorkGroup = FlexibleWorkGroup.builder()
                .flexibleWork(flexibleWork)
                .flexibleWorkGroupName("테스트 그룹")
                .active(true)
                .build();
        flexibleWorkGroupRepository.save(flexibleWorkGroup);
        return flexibleWorkGroup;
    }

    private void saveMandatoryTime(FlexibleWork flexibleWork) {
        List<MandatoryTime> mandatoryTimeList = new ArrayList<>();
        mandatoryTimeList.add(MandatoryTime.builder()
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(12, 0))
                .flexibleWork(flexibleWork)
                        .build());
        mandatoryTimeList.add(MandatoryTime.builder()
                .startTime(LocalTime.of(14, 0))
                .endTime(LocalTime.of(15, 0))
                .flexibleWork(flexibleWork)
                        .build());
        mandatoryTimeRepository.saveAll(mandatoryTimeList);
    }

    private FlexibleWork saveFlexibleWork() {
        FlexibleWork flexibleWork = FlexibleWork.builder()
                .flexibleWorkName("테스트 그룹")
                .flexibleWorkType(FlexibleWorkType.WEEK_52)
                .dailyWorkTime(DailyWorkTime.EIGHT)
                .workDayOfWeeks(Set.of(WorkDayOfWeek.MONDAY, WorkDayOfWeek.SATURDAY))
                .active(true)
                .applyDateFrom(LocalDate.now())
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(18,0))
                .settlementUnitPeriod(SettlementUnitPeriod.ONE_MONTH)
                .mandatoryTimeExist(true)
                .build();
        flexibleWorkRepository.save(flexibleWork);
        return flexibleWork;
    }
}