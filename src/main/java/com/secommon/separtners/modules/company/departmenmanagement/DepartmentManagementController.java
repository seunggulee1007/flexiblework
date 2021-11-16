package com.secommon.separtners.modules.company.departmenmanagement;

import com.secommon.separtners.infra.commons.BaseAnnotation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

@BaseAnnotation
@RequiredArgsConstructor
@RequestMapping("/api/department-management")
public class DepartmentManagementController {

    private final DepartmentManagementService departmentManagementService;



}
