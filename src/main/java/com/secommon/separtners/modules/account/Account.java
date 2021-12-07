package com.secommon.separtners.modules.account;

import com.secommon.separtners.infra.advice.exceptions.BadRequestException;
import com.secommon.separtners.infra.commons.enums.ErrorMessage;
import com.secommon.separtners.infra.security.Jwt;
import com.secommon.separtners.modules.account.enums.AccountRole;
import com.secommon.separtners.modules.authority.authoritygroup.AuthorityGroup;
import com.secommon.separtners.modules.authority.authoritygroup.AuthorityGroupAccount;
import com.secommon.separtners.modules.common.UpdatedEntity;
import com.secommon.separtners.modules.commute.commute.Commute;
import com.secommon.separtners.modules.company.employee.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor @NoArgsConstructor
@NamedEntityGraph(
        name ="Account.withAccountRolesAndEmployee",
        attributeNodes = {
                @NamedAttributeNode( "roles" ),
                @NamedAttributeNode( "employee" )
        }
)
public class Account extends UpdatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="account_id")
    private Long id;

    @Column(unique = true)
    private String email;

    /** 사용자 이름 */
    private String userName;

    /** 비밀번호 */
    private String password;

    @Lob @Basic
    private String emailCheckToken;

    /** 권한 */
    @ElementCollection(fetch = LAZY)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "account_roles", joinColumns = @JoinColumn(name = "account_id"))
    @Builder.Default
    private Set<AccountRole> roles = Set.of(AccountRole.USER);

    /** 이메일 인증 여부 */
    private boolean emailVerified;

    /** 최고 관리자 여부 */
    @Builder.Default
    @ColumnDefault(value="false")
    private boolean superAdmin = false;

    /** 가입일자 */
    private LocalDateTime joinedAt;

    /** 로그인 횟수 */
    private int loginCount;

    /** 로그인 실패 회수 */
    private int loginFailCount;

    /** 마지막 로그인 일자 */
    private LocalDateTime lastLoginAt;

    /** 프로필 이미지 */
    @Lob @Basic
    private String profileImage;

    /** 출퇴근 기록 */
    @OneToMany(mappedBy = "account", fetch = LAZY)
    private List<Commute> commutes;

    /** 사원 정보 */
    @OneToOne(fetch = LAZY, mappedBy = "account")
    private Employee employee;

    @OneToMany(mappedBy = "account", fetch = LAZY)
    @Builder.Default
    private List<AuthorityGroupAccount> authorityGroupAccountList = new ArrayList<>();

    /** 로그인 */
    public void login( PasswordEncoder passwordEncoder, String credentials) {
        if (!passwordEncoder.matches(credentials, this.password)) {
            this.loginFailCount++;
            throw new BadRequestException( ErrorMessage.NOT_MATCHED_ACCOUNT.getMessage());
        } else if(!this.emailVerified) {
            throw new BadRequestException(ErrorMessage.VERIFY_EMAIL.getMessage());
        }
    }

    /** 로그인 후 세팅 */
    public void afterLoginSuccess () {
        this.loginFailCount = 0;
        this.loginCount++;
        this.lastLoginAt = LocalDateTime.now();
    }

    /** 권한 세팅 */
    public void setUserRole ( boolean admin ) {
        if(admin) {
            this.roles = Set.of(AccountRole.USER, AccountRole.ADMIN);
        }else {
            this.roles = Set.of(AccountRole.USER);
        }
    }

    /* 이메일 인증 토큰 세팅 */
    public void generateEmailToken ( Jwt jwt ) {
        Jwt.Claims claims = Jwt.Claims.of(this.id, this.email, this.roles.stream().map( AccountRole::name ).toArray(String[]::new));
        this.emailCheckToken = jwt.createEmailToken(claims);
    }

    /** 이메일 토큰 유효성 체크 */
    public boolean isValidEmailToken ( Jwt jwt, String token ) {
        return jwt.validateToken( token );
    }

    /** 로그인 완료 */
    public void completeSignUp () {
        this.emailVerified = true;
        this.joinedAt = LocalDateTime.now();
    }

    /** 비밀번호 변경 */
    public void changePassword ( String password ) {
        this.password = password;
    }

    /** 프로필 사진 변경 */
    public void changeProfileImage ( String profileImage ) {
        this.profileImage = profileImage;
    }

    public void matchingEmployee(Employee employee) {
        this.employee = employee;
        employee.matchingAccount(this);
    }

    @Override
    public boolean equals ( Object o ) {
        if ( this == o ) return true;
        if ( o == null || Hibernate.getClass( this ) != Hibernate.getClass( o ) ) return false;
        Account account = ( Account ) o;
        return Objects.equals( id, account.id );
    }

    @Override
    public int hashCode () {
        return 0;
    }

    public void removeAuthorityGroup ( AuthorityGroup authorityGroup ) {
        this.authorityGroupAccountList.stream()
                .filter(
                        authorityGroupAccount -> authorityGroupAccount.getAccount() == this
                                && authorityGroupAccount.getAuthorityGroup() == authorityGroup )
                .findFirst()
                .orElseThrow()
            .remove();
    }

}
