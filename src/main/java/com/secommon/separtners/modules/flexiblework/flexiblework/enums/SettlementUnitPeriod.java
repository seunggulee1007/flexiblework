package com.secommon.separtners.modules.flexiblework.flexiblework.enums;

import com.secommon.separtners.modules.common.EnumMapperType;

public enum SettlementUnitPeriod implements EnumMapperType {

    ONE_WEEK("1주", false),
    TWO_WEEK("2주", false),
    ONE_MONTH("1개월", true),
    THREE_MONTH("3개월", false),
    ;

    final String codeName;
    final boolean defaultValue;
    SettlementUnitPeriod(String codeName, boolean defaultValue) {
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
