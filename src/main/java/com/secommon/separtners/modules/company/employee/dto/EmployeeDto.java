package com.secommon.separtners.modules.company.employee.dto;

import com.secommon.separtners.modules.account.Account;
import com.secommon.separtners.modules.company.employee.Employee;
import com.secommon.separtners.modules.company.employeedepartment.EmployeeDepartment;
import com.secommon.separtners.modules.flexiblework.flexiblework.FlexibleWork;
import com.secommon.separtners.modules.flexiblework.flexibleworkgroup.FlexibleWorkGroup;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class EmployeeDto {

    /** 사원 아이디  */
    private Long employeeId;

    /** 사용자 아이디 */
    private Long accountId;

    /** 사용자 이름 */
    private String userName;

    /** 이메일 */
    private String email;

    /** 입사일자 */
    private LocalDateTime hireDate;

    private List<EmployeeDepartment> employeeDepartmentList;

    public EmployeeDto(Employee employee, Account account ) {
        this.employeeId = employee.getId();
        this.accountId = account.getId();
        this.userName = account.getUserName();
        this.email = account.getEmail();
        this.hireDate = employee.getHireDate();
//        this.employeeDepartmentList = employee.getEmployeeDepartmentList();
    }

    public EmployeeDto ( Employee employee ) {
        this.employeeId = employee.getId();
        this.accountId = employee.getAccount().getId();
        this.userName = employee.getAccount().getUserName();
        this.email = employee.getAccount().getEmail();
        this.hireDate = employee.getHireDate();
    }

}
