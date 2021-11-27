package com.secommon.separtners.modules.flexiblework.flexiblework.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class FlexibleWorkSearchForm {

    /** 유연근무 유형 */
    private String flexibleWorkType;

    /** 유연근무 명칭 */
    private String flexibleWorkName;

}
