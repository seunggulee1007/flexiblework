package com.secommon.separtners.modules.account;

import com.secommon.separtners.infra.AbstractContainerBaseTest;
import com.secommon.separtners.infra.MockMvcTest;
import com.secommon.separtners.modules.account.form.SignUpForm;
import com.secommon.separtners.modules.account.repository.AccountRepository;
import com.secommon.separtners.modules.company.employee.Position;
import com.secommon.separtners.modules.main.form.LoginForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@MockMvcTest
class AccountControllerTest extends AbstractContainerBaseTest {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;

    @DisplayName( "회원 가입" )
    @Test
    @Order( 1 )
    void signUp() throws Exception {
        SignUpForm signUpForm = SignUpForm.builder()
                .email( "leesg107@naver.com" )
                .password( "qlalfqjsgh1!" )
                .userName( "관리자" )
                .position( Position.DIRECTOR )
                .admin( true )
                .build();
        this.mockMvc.perform( post("/api/account/sign-up")
                .content( this.objectMapper.writeValueAsString( signUpForm ) )
                .contentType( MediaType.APPLICATION_JSON )
        )
                .andExpect( status().isOk() )
                .andDo( print()
        );

    }

    @DisplayName( "회원가입 실패_이미 사용중인 이메일" )
    @Test
    void failSignUp_already_email() throws Exception {
        saveUser();
        SignUpForm signUpForm = SignUpForm.builder()
                .email( "leesg107@naver.com" )
                .password( "qlalfqjsgh1!" )
                .userName( "관리자" )
                .admin( true )
                .build();
        this.mockMvc.perform( post("/api/account/sign-up")
                        .content( this.objectMapper.writeValueAsString( signUpForm ) )
                        .contentType( MediaType.APPLICATION_JSON )
                )
                .andExpect( status().isBadRequest() )
                .andDo( print()
                );
    }

    @DisplayName( "회원가입 실패_입력값 오류" )
    @Test
    void failSignUp_badRequest() throws Exception {
        saveUser();
        SignUpForm signUpForm = SignUpForm.builder()
                .email( "leesg107@naver.com" )
                .password( "1234" )
                .userName( "관리자" )
                .admin( true )
                .build();
        this.mockMvc.perform( post("/api/account/sign-up")
                        .content( this.objectMapper.writeValueAsString( signUpForm ) )
                        .contentType( MediaType.APPLICATION_JSON )
                )
                .andExpect( status().isBadRequest() )
                .andDo( print()
                );
    }

    @DisplayName( "로그인 실패 - 이메일 인증" )
    @Test
    @Order( 2 )
    void loginFailed_emailNotVerify() throws Exception {
        saveUser();
        String email = "leesg107@naver.com";
        LoginForm loginForm = LoginForm.builder()
                .email( email )
                .password( "qlalfqjsgh1!" )
                .build();
        this.mockMvc.perform( post("/api/sign-in")
                .contentType( MediaType.APPLICATION_JSON)
                .content( this.objectMapper.writeValueAsString( loginForm ) )
        )
                .andDo( print() )
                .andExpect( status().isBadRequest() )
                /*.andExpect( jsonPath( "response" ).exists() )
                .andExpect( jsonPath( "response.accountId" ).exists() )
                .andExpect( jsonPath( "response.email" ).value( email ) )*/

        ;
        Optional<Account> account = accountRepository.findByEmail( email );
        assertNotNull(account);
        account.ifPresent( value -> assertEquals( email, value.getEmail() ) );
    }

    void saveUser() {
        SignUpForm signUpForm = SignUpForm.builder()
                .email( "leesg107@naver.com" )
                .password( "qlalfqjsgh1!" )
                .userName( "관리자" )
                .admin( true )
                .build();
        accountService.processNewAccount( signUpForm );
    }
}