package com.secommon.separtners.modules.account;

import com.secommon.separtners.infra.security.Jwt;
import com.secommon.separtners.modules.account.enums.AccountRole;
import com.secommon.separtners.modules.company.employee.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Set;

import static org.springframework.beans.BeanUtils.copyProperties;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class AccountDto {

    /** 로그인 아이디 */
    private Long accountId;

    /** 이메일 */
    private String email;

    /** 사용자 이름 */
    private String userName;

    /** 가입일자 */
    private LocalDateTime joinedAt;

    /** 권한 */
    private Set<AccountRole> roles;

    /** 로그인 횟수 */
    private int loginCount;

    /** 마지막 로그인 일자 */
    private LocalDateTime lastLoginAt;

    private String accessToken;

    private String refreshToken;

    private String profileImage;

    private String position;

    private String departmentName;

    public AccountDto(Account account) {
        copyProperties(account, this);
        this.accountId = account.getId();
        this.position = account.getEmployee().getPosition().getTitle();
        this.departmentName = account.getEmployee().getEmployeeDepartmentList().isEmpty() ? "" : account.getEmployee().getEmployeeDepartmentList().get( 0 ).getDepartment().getDepartmentName();
    }

    public void generateAccessToken ( Jwt jwt) {
        Jwt.Claims claims = Jwt.Claims.of(accountId, email, roles.stream().map( AccountRole::name ).toArray(String[]::new));
        log.error( "claims :: {} " , claims );
        this.accessToken = jwt.createAccessToken( claims );
        this.refreshToken = jwt.createRefreshToken( claims );
    }

}
