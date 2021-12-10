package com.secommon.separtners.modules.company.employee.form;

import com.secommon.separtners.modules.company.employee.enums.EmployeeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class StatusForm {

    @NotNull(message = "변경을 원하시는 사원을 선택해 주세요.")
    private Long employeeId;

    @NotNull(message = "변경을 원하시는 상태를 선택해 주세요.")
    private EmployeeStatus employeeStatus;

}
