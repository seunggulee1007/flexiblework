package com.secommon.separtners.modules.company.departmenmanagement.repository;

import com.secommon.separtners.modules.common.Querydsl4RepositorySupport;
import com.secommon.separtners.modules.company.departmenmanagement.DepartmentManagement;

public class DepartmentManagementRepositoryImpl extends Querydsl4RepositorySupport implements DepartmentManagementRepositoryQuerydsl {

    public DepartmentManagementRepositoryImpl () {
        super( DepartmentManagement.class);
    }

}
