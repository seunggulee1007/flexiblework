package com.secommon.separtners.modules.company.employee.repository;

import com.secommon.separtners.modules.common.Querydsl4RepositorySupport;
import com.secommon.separtners.modules.company.employee.Employee;
import com.secommon.separtners.modules.company.employee.dto.EmployeeDto;
import com.secommon.separtners.modules.company.employee.form.EmployeeSearchForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.querydsl.core.types.Projections.constructor;
import static com.secommon.separtners.modules.account.QAccount.account;
import static com.secommon.separtners.modules.company.department.QDepartment.department;
import static com.secommon.separtners.modules.company.employee.QEmployee.employee;

@Slf4j
public class EmployeeRepositoryImpl extends Querydsl4RepositorySupport implements EmployeeRepositoryQuerydsl {

    public EmployeeRepositoryImpl() {super( Employee.class);}

    public Page<EmployeeDto> findAllByEmployeeCondition( EmployeeSearchForm condition, Pageable pageable ) {

        return applyPagination(
                pageable, query -> query.select(
                        constructor( EmployeeDto.class, employee, account ) )
                        .from( employee )
                        .leftJoin( employee.accountList, account )
                        .where( account.userName.like( condition.getUserName() ) )
        );
    }

    public Page<EmployeeDto> findAllNotRegistered( Long departmentId, EmployeeSearchForm employeeSearchForm, Pageable pageable ) {
        return applyPagination(pageable, query-> query.select(constructor( EmployeeDto.class, employee, account ))
                .from( employee )
                .leftJoin( employee.accountList, account )
                .leftJoin(account.department, department)
                .where(
                        condition(employeeSearchForm.getUserName(), account.userName::containsIgnoreCase),
                        department.id.eq(departmentId).or(department.id.isNull())
                )
        );
    }

}
