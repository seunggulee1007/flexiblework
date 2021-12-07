package com.secommon.separtners.modules.company.employee;

import com.secommon.separtners.modules.account.Account;
import com.secommon.separtners.modules.common.UpdatedEntity;
import com.secommon.separtners.modules.commute.area.CommuteArea;
import com.secommon.separtners.modules.commute.group.CommuteGroup;
import com.secommon.separtners.modules.company.department.Department;
import com.secommon.separtners.modules.company.employee.enums.EmployeeStatus;
import com.secommon.separtners.modules.company.employee.enums.Position;
import com.secommon.separtners.modules.company.employeedepartment.EmployeeDepartment;
import com.secommon.separtners.modules.company.employeemanagement.EmployeeManagement;
import com.secommon.separtners.modules.flexiblework.flexibleworkgroup.FlexibleWorkGroup;
import com.secommon.separtners.modules.flexiblework.flexibleworkplan.FlexibleWorkPlan;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Slf4j
@Entity
@Builder @Getter
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

    /** 로그인 계정 */
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    /** 사원 부서 매핑 */
    @OneToMany(fetch = LAZY, mappedBy = "employee")
    @Builder.Default
    private List<EmployeeDepartment> employeeDepartmentList = new ArrayList<>();

    /** 사원 관리 */
    @OneToMany(fetch = LAZY, mappedBy = "employee")
    @Builder.Default
    private List<EmployeeManagement> employeeList = new ArrayList<>();

    /** 유연근무 그룹 */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "flexible_work_group_id")
    private FlexibleWorkGroup flexibleWorkGroup;

    @OneToMany(fetch = LAZY, mappedBy = "employee")
    @Builder.Default
    private List<FlexibleWorkPlan> flexibleWorkPlanList = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "commute_group_id")
    private CommuteGroup commuteGroup;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "commute_area_id")
    private CommuteArea commuteArea;

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
        this.flexibleWorkGroup = flexibleWorkGroup;
        if(!this.flexibleWorkGroup.getEmployeeList().contains( this )) {
            this.flexibleWorkGroup.getEmployeeList().add( this );
        }
    }

    public void removeWorkGroup () {
        this.flexibleWorkGroup.getEmployeeList().remove( this );
        this.flexibleWorkGroup = null;
    }

    public void setCommuteGroup(CommuteGroup commuteGroup) {
        if(this.commuteGroup != null && this.commuteGroup.getId() != null ) {
            this.commuteGroup.getEmployeeList().remove(this);
        }
        this.commuteGroup = commuteGroup;
        if(!this.commuteGroup.getEmployeeList().contains(this)) {
            this.commuteGroup.getEmployeeList().add(this);
        }
    }

}
