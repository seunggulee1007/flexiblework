package com.secommon.separtners.modules.flexiblework.calculateworkplan;

import com.secommon.separtners.modules.flexiblework.flexibleworkplan.FlexibleWorkPlan;
import com.secommon.separtners.modules.flexiblework.flexibleworkplan.form.FlexibleWorkPlanForm;

import java.util.List;

public interface CalculateWorkPlanService {

    List<FlexibleWorkPlan> calculateWorkPlan(FlexibleWorkPlanForm flexibleWorkPlanForm);

}
