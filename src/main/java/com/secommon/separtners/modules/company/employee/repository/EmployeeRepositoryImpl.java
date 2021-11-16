package com.secommon.separtners.modules.company.employee.repository;

import com.querydsl.core.QueryResults;
import com.secommon.separtners.modules.common.Querydsl4RepositorySupport;
import com.secommon.separtners.modules.company.employee.Employee;
import com.secommon.separtners.modules.company.employee.EmployeeDto;
import com.secommon.separtners.modules.company.employee.form.EmployeeSearchForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.querydsl.core.types.Projections.constructor;
import static com.secommon.separtners.modules.account.QAccount.account;
import static com.secommon.separtners.modules.company.employee.QEmployee.employee;

public class EmployeeRepositoryImpl extends Querydsl4RepositorySupport implements EmployeeRepositoryQuerydsl {

    public EmployeeRepositoryImpl() {super( Employee.class);}

    public Page<EmployeeDto> findAllByEmployeeCondition( EmployeeSearchForm condition, Pageable pageable ) {

        QueryResults<EmployeeDto> result = select( constructor( EmployeeDto.class, employee, account ) )
                .from( employee )
                .leftJoin( employee.account, account )
                .where( account.userName.like( condition.getUserName() ) )
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetchResults();

        List<EmployeeDto> content = result.getResults();
        long total = result.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

}
