package com.secommon.separtners.modules.account;

import com.secommon.separtners.infra.commons.BaseAnnotation;
import com.secommon.separtners.modules.account.form.SignUpForm;
import com.secommon.separtners.modules.account.validator.SignUpFormValidator;
import com.secommon.separtners.utils.ApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.secommon.separtners.utils.ApiUtil.success;

@Slf4j
@BaseAnnotation
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;
    private final SignUpFormValidator signUpFormValidator;

    @InitBinder("signUpForm")
    public void initBinder( WebDataBinder webDataBinder ) {
        webDataBinder.addValidators( signUpFormValidator );
    }

    /**
     * 회원가입
     * @param signUpForm : 회원가입 폼
     * @return AccountDto
     */
    @PostMapping("/sign-up")
    public ApiUtil.ApiResult<AccountDto> signUp( @Valid @RequestBody SignUpForm signUpForm ) {
        return success( new AccountDto(accountService.processNewAccount( signUpForm )));
    }

    @GetMapping("/check-email-token/{token}/{email}")
    public ApiUtil.ApiResult<Boolean> checkEmailToken( @PathVariable String token, @PathVariable String email) {
        return success( accountService.checkEmailToken( token, email ), "이메일 인증에 성공하였습니다. 로그인을 진행해 주세요." );
    }

}
