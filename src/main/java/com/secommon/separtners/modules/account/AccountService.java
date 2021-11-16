package com.secommon.separtners.modules.account;

import com.secommon.separtners.infra.advice.exceptions.BadRequestException;
import com.secommon.separtners.infra.advice.exceptions.ExpiredTokenException;
import com.secommon.separtners.infra.advice.exceptions.NoMemberException;
import com.secommon.separtners.infra.commons.BaseServiceAnnotation;
import com.secommon.separtners.infra.mail.EmailMessage;
import com.secommon.separtners.infra.mail.EmailService;
import com.secommon.separtners.infra.properties.AppProperties;
import com.secommon.separtners.infra.security.Jwt;
import com.secommon.separtners.infra.security.JwtAuthentication;
import com.secommon.separtners.modules.account.form.PasswordForm;
import com.secommon.separtners.modules.account.form.ProfileImageForm;
import com.secommon.separtners.modules.account.form.SignUpForm;
import com.secommon.separtners.modules.account.repository.AccountRepository;
import com.secommon.separtners.modules.company.employee.Employee;
import com.secommon.separtners.modules.company.employee.repository.EmployeeRepository;
import com.secommon.separtners.modules.main.TokenDto;
import com.secommon.separtners.modules.main.form.LoginForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@BaseServiceAnnotation
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final Jwt jwt;
    private final AppProperties appProperties;
    private final TemplateEngine templateEngine;
    private final EmployeeRepository employeeRepository;

    public Account processNewAccount ( SignUpForm signUpForm ) {
        Account account = saveAccount( signUpForm );
        // 사원 저장
        matchingEmployee( signUpForm, account );
        // 인증용 이메일 토큰
        account.generateEmailToken(jwt);
        // 이메일 전송
        sendSignUpConfirmEmail( account );
        return account;
    }

    private void matchingEmployee ( SignUpForm signUpForm, Account account ) {
        Employee employee = Employee.builder()
                        .employeeCode( signUpForm.getEmployeeCode() )
                        .position( signUpForm.getPosition() )
                    .build();
        employeeRepository.save( employee );
        account.matchingEmployee( employee );
    }

    private Account saveAccount( SignUpForm signUpForm) {
        Account account = Account.builder()
                .email( signUpForm.getEmail() )
                .userName( signUpForm.getUserName() )
                .password( passwordEncoder.encode( signUpForm.getPassword() ) )
                .build();
        // 권한 설정
        account.setUserRole(signUpForm.isAdmin());
        return accountRepository.save( account );
    }

    /**
     * 본인 인증 메일 발송
     * @param account : 인증할 계정 정보
     */
    private void sendSignUpConfirmEmail( Account account ) {
        Context context = new Context();
        String link = "/check-email-token?email=" + account.getEmail() + "&token=" + account.getEmailCheckToken();
        log.error( "mail.link :: {}",  link);
        context.setVariable( "link", link);
        context.setVariable( "userName", account.getUserName() );
        context.setVariable( "linkName", "이메일 인증" );
        context.setVariable( "host", appProperties.getHost() );
        String message = templateEngine.process( "mail/simple-link", context );
        EmailMessage emailMessage = EmailMessage.builder()
                .to( account.getEmail() )
                .subject( "회원 가입 인증" )
                .message( message )
                .build();
        emailService.sendEmail( emailMessage );
    }

    /**
     * 로그인
     * @param loginForm : 로그인 정보
     * @return AccountDto
     */
    public AccountDto login ( LoginForm loginForm ) {
        Account account = accountRepository.findByEmail( loginForm.getEmail() ).orElseThrow(NoMemberException::new);
        account.login( passwordEncoder, loginForm.getPassword() );
        AccountDto accountDto = new AccountDto(account);
        account.afterLoginSuccess();
        // 화면 전송용 토큰 세팅
        accountDto.generateAccessToken(jwt);
        return accountDto;
    }

    /**
     * 가입 이메일 체크
     * @param token : 토큰
     * @param email : 이메일
     * @return Boolean : 이메일 체크 여부
     */
    public boolean checkEmailToken(String token, String email) {
        Account account = accountRepository.findByEmail( email ).orElseThrow(()-> new UsernameNotFoundException( "해당 이메일로 가입된 사용자가 없습니다." ) );
        if (!account.isValidEmailToken(jwt, token)) {
            throw new BadRequestException("유효하지 않은 토큰입니다.");
        }
        account.completeSignUp();
        return true;
    }

    /**
     * 비밀번호 변경
     * @param authentication : 로그인 사용자 정보
     * @param passwordForm : 변경할 비밀번호 정보
     */
    public void changePassword ( JwtAuthentication authentication, PasswordForm passwordForm ) {
        Account originAccount = accountRepository.findById( authentication.accountId ).orElseThrow();
        if( !this.passwordEncoder.matches(passwordForm.getOriginPassword(), originAccount.getPassword() ) ) {
            throw new BadRequestException("기존 비밀번호가 일치하지 않습니다.");
        }
        originAccount.changePassword( this.passwordEncoder.encode( passwordForm.getNewPassword() ) );
    }

    /**
     * 리프레시 토큰으로 토큰 재발행
     * @param tokenDto : 토큰 정보
     */
    public TokenDto renewalTokenByRefreshToken( TokenDto tokenDto) {
        if ( jwt.validateToken( tokenDto.getRefreshToken() ) ) {
            Jwt.Claims claims = jwt.verify( tokenDto.getRefreshToken() );
            Account account = accountRepository.findByEmail( claims.getEmail() ).orElseThrow();
            AccountDto accountDto = new AccountDto(account);
            accountDto.generateAccessToken( jwt );
            tokenDto = TokenDto.builder()
                    .accessToken( accountDto.getAccessToken() )
                    .refreshToken( accountDto.getRefreshToken() )
                    .build();
        } else {
            throw new ExpiredTokenException();
        }
        return tokenDto;
    }

    /**
     * 프로필 이미지 변경
     * @param profileImageForm : 변경용 이미지 정보
     * @param authentication : 로그인 사용자
     */
    public String changeProfileImage ( ProfileImageForm profileImageForm, JwtAuthentication authentication ) {
        Account account = accountRepository.findById( authentication.accountId ).orElseThrow( NoMemberException::new);
        account.changeProfileImage( profileImageForm.getProfileImage() );
        return account.getProfileImage();
    }

}
