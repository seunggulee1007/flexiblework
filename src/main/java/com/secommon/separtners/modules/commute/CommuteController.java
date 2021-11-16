package com.secommon.separtners.modules.commute;

import com.secommon.separtners.infra.advice.exceptions.BadRequestException;
import com.secommon.separtners.infra.commons.BaseAnnotation;
import com.secommon.separtners.infra.security.JwtAuthentication;
import com.secommon.separtners.utils.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Objects;

import static com.secommon.separtners.utils.ApiUtil.success;

@BaseAnnotation
@RequiredArgsConstructor
public class CommuteController {

    private final CommuteService commuteService;
    private final CommuteFormValidator commuteFormValidator;

    @InitBinder("commuteForm")
    public void commuteFormInitBinder( WebDataBinder webDataBinder) {
        webDataBinder.addValidators( commuteFormValidator );
    }

    @GetMapping("/commute/my")
    public ApiUtil.ApiResult<CommuteDto> getMyCommute( @AuthenticationPrincipal JwtAuthentication authentication ) {
        CommuteSearchForm commuteSearchForm = CommuteSearchForm.builder()
                .accountId( authentication.accountId ).build();
        return success(commuteService.getMyCommute(commuteSearchForm));
    }

    @PostMapping("/commute")
    public ApiUtil.ApiResult<CommuteDto> doCommute( @Valid @RequestBody CommuteForm commuteForm, @AuthenticationPrincipal JwtAuthentication authentication) {
        if( !Objects.equals( commuteForm.getAccountId(), authentication.accountId ) ) {
            throw new BadRequestException("일치 하지 않는 사용자 입니다. 로그인 정보를 다시 확인해 주세요.");
        }
        return success(commuteService.doCommute( commuteForm, authentication ), "처리되었습니다.");
    }

}
