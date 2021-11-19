package com.secommon.separtners.modules.company.employee;

import com.secommon.separtners.modules.account.Account;
import com.secommon.separtners.modules.common.UpdatedEntity;
import com.secommon.separtners.modules.company.department.Department;
import com.secommon.separtners.modules.company.employeedepartment.EmployeeDepartment;
import com.secommon.separtners.modules.company.employeemanagement.EmployeeManagement;
import com.secommon.separtners.modules.flexiblework.FlexibleWorkGroup;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Builder
@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employee extends UpdatedEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    /** 사원 번호 */
    private String employeeCode;

    /** 직급 */
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Position position = Position.EMPLOYEE;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EmployeeStatus status = EmployeeStatus.NORMAL;

    /** 입사일자 */
    @Builder.Default
    private LocalDateTime hireDate = LocalDateTime.now();

    /** 퇴사일자 */
    private LocalDate resignationDate;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(fetch = LAZY, mappedBy = "employee")
    @Builder.Default
    private List<EmployeeDepartment> employeeDepartmentList = new ArrayList<>();

    @OneToMany(fetch = LAZY, mappedBy = "employee")
    @Builder.Default
    private List<EmployeeManagement> employeeList = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    private FlexibleWorkGroup flexibleWorkGroup;

    public void updateStatus(EmployeeStatus status) {
        this.status = status;
        if (status.equals( EmployeeStatus.RESIGNATION )) {
           this.resignationDate = LocalDate.now();
        }
    }

    public void updateDepartmentManagement ( Department originDepartment, Department willChangeDepartment ) {
        EmployeeDepartment employeeDepartment = this.employeeDepartmentList.stream().filter( department -> department.getDepartment().equals( originDepartment ) ).findFirst().orElseThrow();
        employeeDepartment.moveDepartment(willChangeDepartment);
    }

    public void matchingAccount ( Account account ) {
        this.account = account;
    }

    public void updatePosition ( Position position ) {
        this.position = position;
    }

    public void setWorkGroup ( FlexibleWorkGroup flexibleWorkGroup ) {
        this.flexibleWorkGroup.getEmployeeList().add( this );
        this.flexibleWorkGroup = flexibleWorkGroup;
    }

}
