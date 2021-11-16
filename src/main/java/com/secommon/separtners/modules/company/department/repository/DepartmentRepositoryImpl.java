package com.secommon.separtners.modules.company.department.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.secommon.separtners.modules.common.Querydsl4RepositorySupport;
import com.secommon.separtners.modules.company.department.Department;
import com.secommon.separtners.modules.company.department.QDepartment;
import com.secommon.separtners.modules.company.department.form.DepartmentSearchCondition;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.secommon.separtners.modules.company.department.QDepartment.department;


public class DepartmentRepositoryImpl extends Querydsl4RepositorySupport implements DepartmentRepositoryQuerydsl {

    public DepartmentRepositoryImpl() {
        super( Department.class);
    }

    QDepartment subDepartment = new QDepartment( "subDepartment" );
    @Override
    public List<Department> findAllDepartment ( DepartmentSearchCondition condition ) {
        return select( department )
                .from( department )
                .leftJoin( department.parent, subDepartment )
                .fetchJoin()
                .where( containDepartmentName(condition.getDepartName()) )
                .fetch();
    }

    private BooleanExpression containDepartmentName ( String departName ) {
        return StringUtils.hasText(departName) ? department.departmentName.containsIgnoreCase( departName ) : null;
    }


}
