package com.secommon.separtners.modules.authority.menu;

import com.secommon.separtners.infra.converter.ConvertStringToCamelCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class MenuDto {

    /** 메뉴 식별자 */
    private Long menuId;

    /** 메뉴 명 */
    private String menuName;

    private int order;

    private Long parentId;

    private boolean page;

    private String menuPath;

    private String routerPath;

    private List<MenuDto> children;

    public MenuDto( Menu menu ) {
        this.menuId = menu.getId();
        this.menuName = menu.getMenuName();
        this.order = menu.getOrder();
        this.page = menu.isPage();
        this.menuPath = menu.getMenuPath();
        if( StringUtils.hasText(menu.getMenuPath())) {
            this.routerPath = ConvertStringToCamelCase.toCamelCase( menu.getMenuPath() );
        }
        if(menu.getParent() != null) {
            this.parentId = menu.getParent().getId();
        }
        this.children = menu.getChildren().stream().map( MenuDto::new ).collect( Collectors.toList());
    }

}
