package com.secommon.separtners.modules.flexiblework.flexiblework.enums;

import com.secommon.separtners.modules.common.EnumMapperType;

public enum WorkDayOfWeek implements EnumMapperType {

    MONDAY("월", false),
    TUESDAY("화", false),
    WEDNESDAY("수", false),
    THURSDAY("목", false),
    FRIDAY("금", false),
    SATURDAY("토", false),
    SUNDAY("일", false),
    ;
    String codeName;
    boolean defaultValue;

    WorkDayOfWeek(String codeName, boolean defaultValue) {
        this.codeName = codeName;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getCode () {
        return name();
    }

    @Override
    public String getTitle () {
        return codeName;
    }

    @Override
    public boolean isDefault () {
        return defaultValue;
    }
}
