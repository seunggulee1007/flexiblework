package com.secommon.separtners.modules.flexiblework.enums;

import com.secommon.separtners.modules.common.EnumMapperType;

public enum FlexibleWorkType implements EnumMapperType {

    WEEK_52("주 52시간", true),
    ELASTIC("탄력근무제", false),
    CHOICE("선택적근무제", false),
    HOME("재택 근무제", false),
    TIME("시차출퇴근제", false),
    ;
    String codeName;
    boolean defaultValue;

    FlexibleWorkType(String codeName, boolean defaultValue) {
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
