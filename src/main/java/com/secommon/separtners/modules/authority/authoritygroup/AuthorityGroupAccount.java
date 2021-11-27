package com.secommon.separtners.modules.authority.authoritygroup;

import com.secommon.separtners.modules.account.Account;
import com.secommon.separtners.modules.common.UpdatedEntity;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorityGroupAccount extends UpdatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_group_account_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "authority_group_id")
    private AuthorityGroup authorityGroup;

    public void remove () {
        this.account.getAuthorityGroupAccountList().remove( this );
        this.authorityGroup.getAuthorityGroupAccountList().remove( this );
        this.account = null;
        this.authorityGroup = null;
    }

}
