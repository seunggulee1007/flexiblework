package com.secommon.separtners.modules.flexiblework;

import com.secommon.separtners.infra.commons.BaseAnnotation;
import com.secommon.separtners.modules.flexiblework.form.FlexibleWorkForm;
import com.secommon.separtners.modules.flexiblework.validator.FlexibleWorkFormValidator;
import com.secommon.separtners.utils.ApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import static com.secommon.separtners.utils.ApiUtil.success;

@Slf4j
@BaseAnnotation
@RequiredArgsConstructor
public class FlexibleWorkController {

    private final FlexibleWorkService flexibleWorkService;
    private final FlexibleWorkFormValidator flexibleWorkFormValidator;

    @InitBinder("flexibleWorkForm")
    public void flexibleWorkFormInitBinder( WebDataBinder webDataBinder ) {
        webDataBinder.addValidators( flexibleWorkFormValidator );
    }

    @GetMapping("/flexible-work/codes")
    public ApiUtil.ApiResult<FlexibleCodeDto> getFlexibleCodeList() {
        return success(flexibleWorkService.getFlexibleCodeList());
    }

    /**
     * 유연근무 등록
     */
    @PostMapping("/flexible-work")
    public ApiUtil.ApiResult<Long> saveNewFlexibleWork( @Valid @RequestBody FlexibleWorkForm flexibleWorkForm ) {
        return success(flexibleWorkService.saveNewFlexibleWork( flexibleWorkForm ), "등록되었습니다.");
    }

}
