package com.secommon.separtners.modules.company.employeemanagement;

import com.secommon.separtners.modules.common.CreatedEntity;
import com.secommon.separtners.modules.company.department.Department;
import com.secommon.separtners.modules.company.employee.Employee;
import com.secommon.separtners.modules.company.employee.enums.EmployeeStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;
import static org.springframework.beans.BeanUtils.copyProperties;

@Entity
@Getter @Builder
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmployeeManagement extends CreatedEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="employee_management_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    /** 원래 부서 */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "origin_department_id")
    private Department originDepartment;

    /** 변경할 부서 */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "will_change_department_id")
    private Department willChangeDepartment;

    /** 사원 상태 */
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    /** 적용 일자 */
    private LocalDate applyDate;

    public EmployeeManagement ( Employee employee) {
        copyProperties(employee, this, "createdDate", "createdBy");
    }

    public void updateStatus() {
        this.employee.updateStatus( this.status );
    }

}
