package com.secommon.separtners.modules.company.employeemanagement;

import com.secommon.separtners.infra.commons.BaseAnnotation;
import com.secommon.separtners.modules.common.CommonMessage;
import com.secommon.separtners.modules.company.employeemanagement.form.EmployeeForm;
import com.secommon.separtners.utils.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import java.util.List;

import static com.secommon.separtners.utils.ApiUtil.success;

@BaseAnnotation
@RequiredArgsConstructor
public class EmployeeManagementController {

    private final EmployeeManagementService employeeManagementService;

    @PostMapping("/employee/department")
    public ApiUtil.ApiResult<List<Long>> registerEmployeeToDepartment( @Valid @RequestBody List<EmployeeForm> employeeFormList ) {
        return success(employeeManagementService.registerEmployeeToDepartment( employeeFormList ), CommonMessage.SUCCESS_REGISTER.getMessage() );
    }

}
