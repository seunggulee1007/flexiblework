package com.secommon.separtners.modules.authority.menu;

import com.secommon.separtners.modules.authority.menu.form.MenuForm;
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

    /** 순서 */
    private int orderNumber;

    /** 메뉴 경로 */
    private String menuPath;

    /** 페이지 여부 */
    @Builder.Default
    private boolean page = false;

    /** 상위 메뉴 */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Menu parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent", fetch = LAZY)
    private List<Menu> children = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "menu", fetch = LAZY)
    List<MenuAuthority> menuAuthorityList = new ArrayList<>();

    public void updateMenu ( MenuForm menuForm ) {
        this.menuName = menuForm.getMenuName();
        this.active = menuForm.isActive();
        this.page = menuForm.isPage();
        this.menuPath = menuForm.getMenuPath();
        this.orderNumber = menuForm.getOrderNumber();
    }

    public void updateParent ( Menu parent ) {
        if(this.parent != null) {
            this.parent.getChildren().remove( this );
        }
        this.parent = parent;
        if(!this.parent.getChildren().contains( this )) {
            this.parent.getChildren().add( this );
        }
    }

    public void updateOrder ( int order ) {
        this.orderNumber = order;
    }

}
