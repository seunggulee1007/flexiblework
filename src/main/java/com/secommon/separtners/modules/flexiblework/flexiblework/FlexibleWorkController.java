package com.secommon.separtners.modules.flexiblework.flexiblework;

import com.secommon.separtners.infra.commons.BaseAnnotation;
import com.secommon.separtners.modules.flexiblework.flexiblework.dto.FlexibleWorkDto;
import com.secommon.separtners.modules.flexiblework.flexiblework.form.FlexibleWorkForm;
import com.secommon.separtners.modules.flexiblework.flexiblework.form.FlexibleWorkSearchForm;
import com.secommon.separtners.modules.flexiblework.flexiblework.validator.FlexibleWorkFormValidator;
import com.secommon.separtners.utils.ApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/flexible-work/list")
    public ApiUtil.ApiResult<Page<FlexibleWorkDto>> findAllBySearchForm( FlexibleWorkSearchForm searchForm, Pageable pageable ) {
        log.error( "flexibleWorkSearchForm :: {}, pageable :: {} ", searchForm, pageable );
        return success( flexibleWorkService.findFlexibleWorkByPageable( searchForm, pageable ) );
    }

    /**
     * 유연근무 등록
     */
    @PostMapping("/flexible-work")
    public ApiUtil.ApiResult<Long> saveNewFlexibleWork( @Valid @RequestBody FlexibleWorkForm flexibleWorkForm ) {
        return success(flexibleWorkService.saveNewFlexibleWork( flexibleWorkForm ), "등록되었습니다.");
    }

}
