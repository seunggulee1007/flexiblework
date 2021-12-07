package com.secommon.separtners.modules.commute.area.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class CommuteAreaSearchForm {

    private int searchKind;

    private String searchKeyword;

}
