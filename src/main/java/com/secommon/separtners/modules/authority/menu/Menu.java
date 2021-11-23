package com.secommon.separtners.modules.authority.menu;

import com.secommon.separtners.modules.authority.menuauthority.MenuAuthority;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Builder @Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    /** 메뉴 명 */
    private String menuName;

    /** 사용 여부 */
    private boolean active;

    /** 상위 메뉴 */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Menu parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent", fetch = LAZY)
    private List<Menu> children = new ArrayList<>();

    @OneToMany(fetch = LAZY)
    List<MenuAuthority> menuAuthorityList;

}
