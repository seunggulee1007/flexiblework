package com.secommon.separtners.modules.account.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProfileImageForm {

    @NotBlank(message = "프로필 사진을 선택해 주세요.")
    private String profileImage;

}
