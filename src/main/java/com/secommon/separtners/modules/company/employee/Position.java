package com.secommon.separtners.modules.company.employee;

import com.secommon.separtners.modules.common.EnumMapperType;

public enum Position implements EnumMapperType {

    EMPLOYEE("사원"),  // 사원
    CHIEF("주임"),  // 주임, 계장
    ASSISTANT_MANAGER("대리"),  // 대리
    MANAGER("과장"),    // 과장
    DEPUTY_GENERAL_MANAGER("차장"), // 차장
    GENERAL_MANAGER("부장"),    // 부장, 본부장, 실장 등등
    ADVISOR("고문"),    //고문
    AUDITOR_GENERAL("감사"),
    DIRECTOR("이사"),
    MANAGING_DIRECTOR("상무이사"),
    SENIOR_MANAGING_DIRECTOR("전무이사"),
    SENIOR_EXECUTIVE_VICE_PRESIDENT("부사장"),
    PRESIDENT("사장"),
    VICE_CHAIRMAN("부회장"),
    CHAIRMAN("회장"),
    ;

    private final String positionName;

    Position(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionName () {
        return positionName;
    }


    @Override
    public String getCode () {
        return name();
    }

    @Override
    public String getTitle () {
        return positionName;
    }

    @Override
    public boolean isDefault () {
        return false;
    }

}
