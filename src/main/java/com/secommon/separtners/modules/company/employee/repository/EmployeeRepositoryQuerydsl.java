package com.secommon.separtners.modules.company.employee.repository;

import com.secommon.separtners.modules.company.employee.dto.EmployeeDto;
import com.secommon.separtners.modules.company.employee.dto.AccountWorkDto;
import com.secommon.separtners.modules.company.employee.form.EmployeeSearchForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeRepositoryQuerydsl {

    Page<EmployeeDto> findAllByEmployeeCondition( EmployeeSearchForm condition, Pageable pageable );

    Page<EmployeeDto> findAllNotRegistered( Long departmentId, EmployeeSearchForm condition, Pageable pageable );

}
