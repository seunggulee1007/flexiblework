package com.secommon.separtners.modules.flexiblework.calculateworkplan;

import com.secommon.separtners.infra.advice.exceptions.BadRequestException;
import com.secommon.separtners.infra.commons.BaseServiceAnnotation;
import com.secommon.separtners.modules.account.Account;
import com.secommon.separtners.modules.account.repository.AccountRepository;
import com.secommon.separtners.modules.flexiblework.flexiblework.FlexibleWork;
import com.secommon.separtners.modules.flexiblework.flexiblework.MandatoryTime;
import com.secommon.separtners.modules.flexiblework.flexibleworkplan.FlexibleWorkPlan;
import com.secommon.separtners.modules.flexiblework.flexibleworkplan.form.FlexibleWorkPlanForm;
import com.secommon.separtners.modules.flexiblework.flexibleworkplan.form.FlexibleWorkPlanTimeForm;
import com.secommon.separtners.utils.TimeUtil;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@BaseServiceAnnotation
@RequiredArgsConstructor
public class CalculateWorkPlanServiceImpl implements CalculateWorkPlanService {

    private final AccountRepository accountRepository;

    @Override
    public List<FlexibleWorkPlan> calculateWorkPlan(FlexibleWorkPlanForm flexibleWorkPlanForm) {

        Account account = accountRepository.findById(flexibleWorkPlanForm.getAccountId()).orElseThrow();
        List<MandatoryTime> mandatoryTimeList = getMandatoryTimes(account);
        List<FlexibleWorkPlan> flexibleWorkPlanList = new ArrayList<>();
        for (FlexibleWorkPlanTimeForm flexibleWorkPlanTimeForm : flexibleWorkPlanForm.getFlexibleWorkPlanTimeFormList()) {
            // 의무 시간이 있다면
            checkMandatoryTime(mandatoryTimeList, flexibleWorkPlanTimeForm);

            FlexibleWorkPlan flexibleWorkPlan = makeFlexibleWorkPlan(account, flexibleWorkPlanTimeForm);
            flexibleWorkPlanList.add(flexibleWorkPlan);
        }
        return flexibleWorkPlanList;
    }

    private FlexibleWorkPlan makeFlexibleWorkPlan(Account account, FlexibleWorkPlanTimeForm flexibleWorkPlanTimeForm) {
        FlexibleWorkPlan flexibleWorkPlan = FlexibleWorkPlan.builder()
                .planDate(flexibleWorkPlanTimeForm.getPlanDate())
                .startTime(flexibleWorkPlanTimeForm.getStartTime())
                .endTime(flexibleWorkPlanTimeForm.getEndTime())
                .account(account)
                .build();
        flexibleWorkPlan.calcDate();
        return flexibleWorkPlan;
    }

    private void checkMandatoryTime(List<MandatoryTime> mandatoryTimeList, FlexibleWorkPlanTimeForm flexibleWorkPlanTimeForm) {
        if(!mandatoryTimeList.isEmpty()) {
            for (MandatoryTime mandatoryTime : mandatoryTimeList) {
                TimeUtil timeUtil = TimeUtil.of(mandatoryTime.getStartTime(), mandatoryTime.getEndTime());
                if(!timeUtil.isContains(flexibleWorkPlanTimeForm.getStartTime(), flexibleWorkPlanTimeForm.getEndTime())) {
                    throw new BadRequestException("의무시간은 반드시 포함되어야 합니다. 의무시간은 " + mandatoryTime.getStartTime() + " ~ " + mandatoryTime.getEndTime() + "입니다.");
                }
            }
        }
    }

    private List<MandatoryTime> getMandatoryTimes(Account account) {
        FlexibleWork flexibleWork = account.getFlexibleWorkGroup().getFlexibleWork();
        return flexibleWork.getMandatoryTimeList();
    }
}
