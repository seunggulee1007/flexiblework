package com.secommon.separtners.modules.flexiblework.enums;

import com.secommon.separtners.modules.common.EnumMapperType;

public enum DailyWorkTime implements EnumMapperType {

    SIX("6시간", false),
    SEVEN("7시간", false),
    EIGHT("8시간", true),
    ;

    String codeName;
    boolean defaultValue;
    DailyWorkTime(String codeName, boolean defaultValue) {
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
