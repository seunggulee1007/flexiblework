package com.secommon.separtners.modules.main;


import com.secommon.separtners.infra.commons.BaseAnnotation;
import com.secommon.separtners.infra.security.JwtAuthenticationToken;
import com.secommon.separtners.modules.account.dto.AccountDto;
import com.secommon.separtners.modules.account.AccountService;
import com.secommon.separtners.modules.main.form.LoginForm;
import com.secommon.separtners.utils.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import static com.secommon.separtners.utils.ApiUtil.success;

@BaseAnnotation
@RequiredArgsConstructor
public class MainController {

    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;


    /**
     * 로그인
     * @param loginForm : 로그인 폼
     * @return AccountDto
     */
    @PostMapping("/sign-in")
    public ApiUtil.ApiResult<AccountDto> signIn( @Valid @RequestBody LoginForm loginForm ) {
        Authentication authentication = authenticationManager.authenticate(
                new JwtAuthenticationToken(loginForm.getEmail(), loginForm.getPassword())
        );
        final AccountDto accountDto = (AccountDto)authentication.getDetails();
        return success(accountDto);
    }

    @GetMapping("/sign/refresh-token/{refreshToken}")
    public ApiUtil.ApiResult<TokenDto> renewalTokenByRefreshToken( @PathVariable("refreshToken") TokenDto tokenDto) {
        return success( accountService.renewalTokenByRefreshToken( tokenDto ) );
    }

}
