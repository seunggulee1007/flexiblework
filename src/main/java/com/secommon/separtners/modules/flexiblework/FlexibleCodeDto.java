package com.secommon.separtners.modules.flexiblework;

import com.secommon.separtners.modules.common.EnumMapperValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class FlexibleCodeDto {

    List<EnumMapperValue> flexibleWorkTypeList;

    List<EnumMapperValue> dailyWorkTimeList;

    List<EnumMapperValue> settlementUnitPeriodList;

    List<EnumMapperValue> workDayOfWeekList;

}
