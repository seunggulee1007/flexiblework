package com.secommon.separtners.modules.flexiblework.flexibleworkplan.form;

import lombok.Data;

import java.util.List;

@Data
public class FlexibleWorkPlanForm {

    /** 적용 사원 아이디 */
    private Long employeeId;

    List<FlexibleWorkPlanTimeForm> flexibleWorkPlanTimeFormList;

}
