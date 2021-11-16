package com.secommon.separtners.modules.account;

import com.secommon.separtners.infra.security.JwtAuthentication;
import com.secommon.separtners.modules.account.form.PasswordForm;
import com.secommon.separtners.modules.account.form.ProfileImageForm;
import com.secommon.separtners.modules.account.validator.PasswordFormValidator;
import com.secommon.separtners.utils.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.secommon.separtners.utils.ApiUtil.success;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/settings")
public class SettingController {

    private final AccountService accountService;

    private final PasswordFormValidator passwordFormValidator;

    @InitBinder("passwordForm")
    public void passwordInitBinder( WebDataBinder webDataBinder) {
        webDataBinder.addValidators( passwordFormValidator );
    }

    /**
     * 비밀번호 변경
     * @param passwordForm : 비밀번호변경 폼
     * @return PasswordForm : 변경된 비밀번호 폼
     */
    @PutMapping("/password")
    public ApiUtil.ApiResult<Object> changePassword( @Valid @RequestBody PasswordForm passwordForm, @AuthenticationPrincipal JwtAuthentication authentication ) {
        accountService.changePassword(authentication, passwordForm);
        return success("비밀번호가 변경되었습니다.");
    }

    @PutMapping("/profileImage")
    public ApiUtil.ApiResult<String> changeProfileImage( @Valid @RequestBody ProfileImageForm profileImageForm, @AuthenticationPrincipal JwtAuthentication authentication ) {
        String changeImage = accountService.changeProfileImage(profileImageForm, authentication);
        return success( changeImage, "프로필 이미지를 변경하였습니다." );
    }

}
