package com.secommon.separtners.modules.authority.menuauthority;

import com.secommon.separtners.modules.authority.authoritygroup.AuthorityGroup;
import com.secommon.separtners.modules.authority.menu.Menu;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Builder @Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuAuthority {

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

}