package com.secommon.separtners.modules.company.department;

import com.secommon.separtners.infra.commons.BaseAnnotation;
import com.secommon.separtners.modules.company.department.form.DepartmentForm;
import com.secommon.separtners.modules.company.department.form.DepartmentSearchCondition;
import com.secommon.separtners.modules.company.department.form.DepartmentUpdateForm;
import com.secommon.separtners.modules.company.department.validator.DepartmentRegisterFormValidator;
import com.secommon.separtners.utils.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.secommon.separtners.utils.ApiUtil.success;

@BaseAnnotation
@RequiredArgsConstructor
@RequestMapping("/api/department")
public class DepartmentController {

    private final DepartmentRegisterFormValidator departmentRegisterFormValidator;
    private final DepartmentService departmentService;

    @InitBinder("departmentRegisterForm")
    public void registerInitBinder( WebDataBinder webDataBinder ) {
        webDataBinder.addValidators( departmentRegisterFormValidator );
    }

    @GetMapping("/list")
    public ApiUtil.ApiResult<List<DepartmentDto>> selectDepartmentList( DepartmentSearchCondition condition) {
        return success(departmentService.findAllDepartmentList( condition ));
    }

    @PostMapping
    public ApiUtil.ApiResult<Long> registerDepartmentManagement( @Valid @RequestBody DepartmentForm departmentForm ) {
        return success( departmentService.createNewDepartment( departmentForm ), "부서 등록이 완료되었습니다.");
    }

    @PutMapping
    public ApiUtil.ApiResult<Long> updateDepartmentManagement ( @Valid @RequestBody DepartmentUpdateForm departmentUpdateForm) {
        return success( departmentService.updateDepartment( departmentUpdateForm ), "부서 수정이 완료되었습니다." );
    }

}
