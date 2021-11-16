package com.secommon.separtners.modules.company.employeemanagement.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class EmployeeForm {

    /** 매핑 아이디 */
    private Long employeeDepartmentId;

    /**  부서 아이디 */
    @NotNull(message = "부서를 선택해 주세요.")
    private Long departmentId;

    /** 사원 아이디 */
    @NotNull(message = "사원을 선택해 주세요.")
    private Long employeeId;

    /** 즉시 여부 */
    private boolean rightNow;

    /** 적용 일자 */
    private LocalDate applyDate;

}
