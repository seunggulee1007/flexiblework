package com.secommon.separtners.modules.company.departmenmanagement;

import com.secommon.separtners.infra.commons.BaseServiceAnnotation;
import com.secommon.separtners.modules.company.departmenmanagement.repository.DepartmentManagementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@BaseServiceAnnotation
@RequiredArgsConstructor
public class DepartmentManagementService {

    private final DepartmentManagementRepository departmentManagementRepository;

}
