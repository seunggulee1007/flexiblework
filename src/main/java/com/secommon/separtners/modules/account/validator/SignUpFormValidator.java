package com.secommon.separtners.modules.account.validator;


import com.secommon.separtners.modules.account.repository.AccountRepository;
import com.secommon.separtners.modules.account.form.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports ( Class<?> clazz ) {
        return clazz.isAssignableFrom( SignUpForm.class);
    }

    @Override
    public void validate ( @NonNull Object target, @NonNull Errors errors ) {
        SignUpForm signUpForm = (SignUpForm ) target;
        if(accountRepository.existsByEmail(signUpForm.getEmail())) {
            errors.rejectValue( "email", "invalid.email", new Object[] { signUpForm.getEmail() }, "이미 사용중인 이메일입니다." );
        }
    }

}
