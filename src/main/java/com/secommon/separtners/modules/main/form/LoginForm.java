package com.secommon.separtners.modules.main.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginForm {

    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email(message = "이메일을 올바르게 입력해 주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Pattern( regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@!%*#?&])[A-Za-z\\d$@!%*#?&]{8,12}$", message = "숫자와 영문자, 특수 문자 조합으로 8~12자리를 사용해야 합니다.")
    private String password;

}
