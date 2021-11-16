package com.secommon.separtners.modules.company.employee.form;

import com.secommon.separtners.modules.company.employee.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class PositionForm {

    @NotNull(message = "변경을 원하시는 사원을 선택해 주세요.")
    private Long employeeId;

    @NotNull(message = "변경을 원하시는 직급을 선택해 주세요.")
    private Position position;

}
