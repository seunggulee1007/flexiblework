package com.secommon.separtners.modules.flexiblework.enums;

import com.secommon.separtners.modules.common.EnumMapperType;

public enum WorkDayOfWeek implements EnumMapperType {

    MONDAY("월요일", false),
    TUESDAY("화요일", false),
    WEDNESDAY("수요일", false),
    THURSDAY("목요일", false),
    FRIDAY("금요일", false),
    SATURDAY("토요일", false),
    SUNDAY("일요일", false),
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
