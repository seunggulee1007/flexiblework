package com.secommon.separtners.modules.company.employee;

import com.secommon.separtners.modules.account.Account;
import com.secommon.separtners.modules.common.UpdatedEntity;
import com.secommon.separtners.modules.company.employee.enums.EmployeeStatus;
import com.secommon.separtners.modules.company.employee.enums.Position;
import com.secommon.separtners.modules.company.employeemanagement.EmployeeManagement;
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
    @OneToMany(fetch = LAZY, mappedBy = "employee")
    @Builder.Default
    private List<Account> accountList = new ArrayList<>();

    /** 사원 관리 */
    @OneToMany(fetch = LAZY, mappedBy = "employee")
    @Builder.Default
    private List<EmployeeManagement> employeeManagementList = new ArrayList<>();



    public void updateStatus(EmployeeStatus status) {
        this.status = status;
        if (status.equals( EmployeeStatus.RESIGNATION )) {
           this.resignationDate = LocalDate.now();
        }
    }

    public void updatePosition ( Position position ) {
        this.position = position;
    }

}
