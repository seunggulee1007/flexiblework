package com.secommon.separtners.modules.company.department.validator;


import com.secommon.separtners.infra.commons.enums.ErrorMessage;
import com.secommon.separtners.modules.company.department.form.DepartmentForm;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DepartmentRegisterFormValidator implements Validator {

    @Override
    public boolean supports ( @NonNull Class<?> clazz ) {
        return DepartmentForm.class.isAssignableFrom( clazz );
    }

    @Override
    public void validate ( @NonNull Object target, @NonNull Errors errors ) {
        DepartmentForm departmentForm = ( DepartmentForm ) target;
        if( departmentForm.isRightNow() && departmentForm.getApplyDate() != null) {
            errors.rejectValue( "applyDate", ErrorMessage.WRONG_VALUE.getMessage(), "즉시등록일 경우 적용일자를 입력하실 수 없습니다." );
        } else if( !departmentForm.isRightNow() && departmentForm.getApplyDate() == null) {
            errors.rejectValue( "applyDate", ErrorMessage.WRONG_VALUE.getMessage(), "적용일자가 누락되었습니다." );
        }
    }

}
