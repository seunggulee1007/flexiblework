package com.secommon.separtners.modules.company.departmenmanagement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static org.springframework.beans.BeanUtils.copyProperties;

@Data
@NoArgsConstructor @AllArgsConstructor
public class DepartmentManagementDto {

    private Long departmentManagementId;

    /** 부서명 */
    private String departmentName;

    public DepartmentManagementDto ( DepartmentManagement departmentManagement ) {
        copyProperties(departmentManagement, this);
    }

}
