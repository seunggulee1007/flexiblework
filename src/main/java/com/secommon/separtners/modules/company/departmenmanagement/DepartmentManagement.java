package com.secommon.separtners.modules.company.departmenmanagement;

import com.secommon.separtners.modules.common.UpdatedEntity;
import com.secommon.separtners.modules.company.department.Department;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DepartmentManagement extends UpdatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_management_id")
    private Long id;

    /** 부서명 */
    private String departmentName;

    /** 부서 코드 */
    private String departmentCode;

    /** 사용 여부 */
    private boolean active;

    /** 적용 일자 */
    private LocalDate applyDate;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

}
