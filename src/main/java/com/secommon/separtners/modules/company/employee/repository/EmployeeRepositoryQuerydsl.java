package com.secommon.separtners.modules.company.employee.repository;

import com.secommon.separtners.modules.company.employee.EmployeeDto;
import com.secommon.separtners.modules.company.employee.form.EmployeeSearchForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeRepositoryQuerydsl {

    Page<EmployeeDto> findAllByEmployeeCondition( EmployeeSearchForm condition, Pageable pageable );

}
