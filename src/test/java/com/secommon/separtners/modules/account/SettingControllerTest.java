package com.secommon.separtners.modules.account;

import com.secommon.separtners.infra.AbstractContainerBaseTest;
import com.secommon.separtners.infra.MockMvcTest;
import com.secommon.separtners.infra.commons.enums.ErrorMessage;
import com.secommon.separtners.infra.security.WithMockJwtAuthentication;
import com.secommon.separtners.modules.account.form.PasswordForm;
import com.secommon.separtners.modules.account.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
class SettingControllerTest extends AbstractContainerBaseTest {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AccountRepository accountRepository;

    @DisplayName( "비밀번호 변경 실패 - 기존 비밀번호 입력 오류" )
    @Test
    @WithMockJwtAuthentication
    void failChangePassword_origin_password_wrong() throws Exception {
        String originPassword = "qlalfqjsgh";
        PasswordForm passwordForm = PasswordForm.builder()
                .originPassword( originPassword )
                .newPassword( "admin1!2@" )
                .newPasswordConfirm( "admin1!2@" )
                .build();
        this.mockMvc.perform( put("/api/settings/password")
                .contentType( MediaType.APPLICATION_JSON )
                .content( this.objectMapper.writeValueAsString( passwordForm ) )
        )
                .andDo( print() )
                .andExpect( jsonPath("success").value(false) )
                .andExpect( status().isBadRequest() )
        ;
        Account account = accountRepository.findByEmail( "leesg107@naver.com" ).orElseThrow();
        assertFalse( passwordEncoder.matches( originPassword, account.getPassword() ) );
    }

    @DisplayName( "비밀번호 변경 실패 - 변경 비밀번호 포맷 오류" )
    @Test
    @WithMockJwtAuthentication
    void failChangePassword_change_password_pattern_wrong() throws Exception {
        String originPassword = "qlalfqjsgh1!";
        PasswordForm passwordForm = PasswordForm.builder()
                .originPassword( originPassword )
                .newPassword( "admin" )
                .newPasswordConfirm( "admin" )
                .build();
        this.mockMvc.perform( put("/api/settings/password")
                .contentType( MediaType.APPLICATION_JSON )
                .content( this.objectMapper.writeValueAsString( passwordForm ) )
        )
                .andDo( print() )
                .andExpect( jsonPath("success").value(false) )
                .andExpect( status().isBadRequest() )
                .andExpect( jsonPath( "message" ).value( "숫자와 영문자, 특수 문자 조합으로 8~12자리를 사용해야 합니다." ) )
        ;
        Account account = accountRepository.findByEmail( "leesg107@naver.com" ).orElseThrow();
        assertTrue( passwordEncoder.matches( originPassword, account.getPassword() ) );
    }

    @DisplayName( "비밀번호 변경 실패 - 변경 비밀번호 일치 오류" )
    @Test
    @WithMockJwtAuthentication
    void failChangePassword_change_password_confirm_wrong() throws Exception {
        String originPassword = "qlalfqjsgh1!";
        String newPassword = "qlalfqjsgh1!";
        String newPasswordConfirm = "qlalfqjsgh1234";
        PasswordForm passwordForm = PasswordForm.builder()
                .originPassword( originPassword )
                .newPassword( newPassword )
                .newPasswordConfirm( newPasswordConfirm )
                .build();
        this.mockMvc.perform( put("/api/settings/password")
                .contentType( MediaType.APPLICATION_JSON )
                .content( this.objectMapper.writeValueAsString( passwordForm ) )
        )
                .andDo( print() )
                .andExpect( jsonPath("success").value(false) )
                .andExpect( status().isBadRequest() )
                .andExpect( jsonPath( "message" ).value( ErrorMessage.NEW_PASSWORD.getMessage() ) )
        ;
        Account account = accountRepository.findByEmail( "leesg107@naver.com" ).orElseThrow();
        assertTrue( passwordEncoder.matches( originPassword, account.getPassword() ) );
        assertNotEquals( newPassword, newPasswordConfirm );
    }

    @DisplayName( "비밀번호 변경 성공" )
    @Test
    @WithMockJwtAuthentication
    void successChangePassword() throws Exception {
        String originPassword = "qlalfqjsgh1!";
        String newPassword = "qlqjs1!@#";
        String newPasswordConfirm = "qlqjs1!@#";
        PasswordForm passwordForm = PasswordForm.builder()
                .originPassword( originPassword )
                .newPassword( newPassword )
                .newPasswordConfirm( newPasswordConfirm )
                .build();
        this.mockMvc.perform( put("/api/settings/password")
                .contentType( MediaType.APPLICATION_JSON )
                .content( this.objectMapper.writeValueAsString( passwordForm ) )
        )
                .andDo( print() )
                .andExpect( jsonPath("success").value(true) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "message" ).value( "비밀번호가 변경되었습니다." ) )
        ;
        Account account = accountRepository.findByEmail( "leesg107@naver.com" ).orElseThrow();
        assertTrue( passwordEncoder.matches( newPassword, account.getPassword() ) );
        assertFalse( passwordEncoder.matches( originPassword, account.getPassword() ) );
    }

}