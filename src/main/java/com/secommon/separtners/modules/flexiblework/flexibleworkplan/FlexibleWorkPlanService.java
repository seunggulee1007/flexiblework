package com.secommon.separtners.modules.flexiblework.flexibleworkplan;

import com.secommon.separtners.infra.commons.BaseServiceAnnotation;
import com.secommon.separtners.modules.account.repository.AccountRepository;
import com.secommon.separtners.modules.company.employee.dto.AccountWorkDto;
import com.secommon.separtners.modules.flexiblework.calculateworkplan.CalculateWorkPlanService;
import com.secommon.separtners.modules.flexiblework.flexibleworkplan.form.FlexibleWorkPlanForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@BaseServiceAnnotation
@RequiredArgsConstructor
public class FlexibleWorkPlanService {

    private final FlexibleWorkPlanRepository flexibleWorkPlanRepository;

    private final AccountRepository accountRepository;

    private final CalculateWorkPlanService calculateWorkPlanService;


    public int saveFlexibleWorkPlan(FlexibleWorkPlanForm flexibleWorkPlanForm) {
        List<FlexibleWorkPlan> flexibleWorkPlans = calculateWorkPlanService.calculateWorkPlan(flexibleWorkPlanForm);
        flexibleWorkPlanRepository.saveAll(flexibleWorkPlans);
        return flexibleWorkPlans.size();
    }

    public AccountWorkDto findFlexibleWorkPlanByAccount(Long accountId) {
        return accountRepository.findAllWithWorkGroupAndWorkAndTimes(accountId);
    }

}
