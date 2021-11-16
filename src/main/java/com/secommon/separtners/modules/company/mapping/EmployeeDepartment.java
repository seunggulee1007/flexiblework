package com.secommon.separtners.modules.company.mapping;

import com.secommon.separtners.modules.company.department.Department;
import com.secommon.separtners.modules.company.employee.Employee;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmployeeDepartment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    // 사원의 부서만 변경하기 때문에 사원이 변경되는 일은 없이 부서만 변경.
    public void moveDepartment ( Department willChangeDepartment ) {
        this.department = willChangeDepartment;
    }

    public void register () {
        this.department.getEmployeeDepartmentList().add( this );
        this.employee.getEmployeeDepartmentList().add( this );
    }

}
