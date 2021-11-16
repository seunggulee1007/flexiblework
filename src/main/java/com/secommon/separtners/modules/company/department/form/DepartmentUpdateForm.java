package com.secommon.separtners.modules.company.department.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
public class DepartmentUpdateForm {

    private Long departmentId;

    @NotEmpty(message = "부서 명은 필수입니다.")
    private String departmentName;

    private String departmentCode;

    private Long parentId;

    private LocalDate applyDate;

    private boolean active;

    private boolean rightNow;
    
}
