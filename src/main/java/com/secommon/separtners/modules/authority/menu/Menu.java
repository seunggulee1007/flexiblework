package com.secommon.separtners.modules.authority.menu;

import com.secommon.separtners.modules.authority.menu.form.MenuForm;
import com.secommon.separtners.modules.authority.menu.form.MenuUpdateForm;
import com.secommon.separtners.modules.authority.menuauthority.MenuAuthority;
import com.secommon.separtners.modules.common.UpdatedEntity;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Builder @Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends UpdatedEntity {

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

    public void updateMenu (MenuUpdateForm menuUpdateForm ) {
        this.menuName = menuUpdateForm.getMenuName();
        this.active = menuUpdateForm.isActive();
        this.page = menuUpdateForm.isPage();
        this.menuPath = menuUpdateForm.getMenuPath();
        this.orderNumber = menuUpdateForm.getOrderNumber();
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
