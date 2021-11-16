package com.secommon.separtners.modules.flexiblework.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class FlexibleWorkGroupForm {

    private Long flexibleWorkGroupId;

    private List<Long> departmentIds;

    private List<Long> employeeIds;

}
