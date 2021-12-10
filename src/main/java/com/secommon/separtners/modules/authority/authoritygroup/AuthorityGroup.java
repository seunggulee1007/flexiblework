package com.secommon.separtners.modules.authority.authoritygroup;

import com.secommon.separtners.modules.authority.authoritygroup.form.AuthorityGroupUpdateForm;
import com.secommon.separtners.modules.authority.menuauthority.MenuAuthority;
import com.secommon.separtners.modules.common.UpdatedEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorityGroup extends UpdatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_group_id")
    private Long id;

    /** 권한 그룹 명 */
    private String authorityGroupName;

    /** 사용 여부 */
    private boolean active;

    /** 기본 여부 */
    private boolean basic;

    /** 관리자 여부 */
    private boolean admin;

    @OneToMany(mappedBy = "authorityGroup", fetch = LAZY)
    private List<AuthorityGroupAccount> authorityGroupAccountList;

    @OneToMany(mappedBy = "authorityGroup", fetch = LAZY)
    List<MenuAuthority> menuAuthorityList;

    public void updateByAuthorityGroupUpdateForm(AuthorityGroupUpdateForm authorityGroupUpdateForm) {
        this.active = authorityGroupUpdateForm.isActive();
        this.basic = authorityGroupUpdateForm.isBasic();
        this.admin = authorityGroupUpdateForm.isAdmin();
        this.authorityGroupName = authorityGroupUpdateForm.getAuthorityGroupName();
    }

}
