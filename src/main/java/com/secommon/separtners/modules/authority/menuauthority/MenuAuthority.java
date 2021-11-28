package com.secommon.separtners.modules.authority.menuauthority;

import com.secommon.separtners.modules.authority.authoritygroup.AuthorityGroup;
import com.secommon.separtners.modules.authority.menu.Menu;
import com.secommon.separtners.modules.common.UpdatedEntity;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Builder @Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuAuthority extends UpdatedEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_authority_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "authority_group_id")
    private AuthorityGroup authorityGroup;

    @Enumerated(EnumType.STRING)
    private MenuRole menuRole;

    public void changeMenu(Menu menu) {
        this.menu = menu;
    }

    public void changeAuthorityGroup(AuthorityGroup authorityGroup) {
        this.authorityGroup = authorityGroup;
    }
}