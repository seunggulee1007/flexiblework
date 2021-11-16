package com.secommon.separtners.modules.company.department.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class DepartmentForm {

    @NotEmpty(message = "부서명을 입력해 주세요.")
    private String departmentName;

    private String departmentCode;

    private Long parentId;

    private LocalDate applyDate;

    private boolean rightNow;
    
}
