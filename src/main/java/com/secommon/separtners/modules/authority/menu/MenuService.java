package com.secommon.separtners.modules.authority.menu;

import com.secommon.separtners.infra.advice.exceptions.BadRequestException;
import com.secommon.separtners.infra.commons.BaseServiceAnnotation;
import com.secommon.separtners.modules.authority.menu.form.MenuForm;
import com.secommon.separtners.modules.authority.menu.form.MenuUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@BaseServiceAnnotation
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public List<MenuDto> findAllMenu() {
        List<Menu> menuList = menuRepository.findAll();
        List<Menu> collect = menuList.stream().filter( menu -> menu.getParent() == null ).collect( Collectors.toList());
        MenuDto menuDto = new MenuDto(collect.get( 0 ));
        return Collections.singletonList(menuDto);
    }

    public Long saveMenu(MenuForm menuForm ) {
        Menu menu = Menu.builder()
                .menuName( menuForm.getMenuName() )
                .active( menuForm.isActive() )
                .orderNumber( menuForm.getOrderNumber() )
                .page( menuForm.isPage() )
                .menuPath( menuForm.getMenuPath() )
                .build();
        Menu parent = menuRepository.findById( menuForm.getParentId() ).orElseThrow();
        menuRepository.save( menu );
        menu.updateParent( parent );
        return menu.getId();
    }

    public Long updateMenu (MenuUpdateForm menuUpdateForm ) {
        Menu menu = menuRepository.findById( menuUpdateForm.getMenuId() ).orElseThrow();
        menu.updateMenu( menuUpdateForm );
        if( !menu.getParent().getId().equals( menuUpdateForm.getParentId() ) ) {
            Menu parent = menuRepository.findById( menuUpdateForm.getParentId() ).orElseThrow();
            menu.updateParent(parent);
        }
        return menu.getId();
    }

    public List<MenuUpdateForm> updateOrder(List<MenuUpdateForm> menuUpdateFormList) {

        if(menuUpdateFormList.isEmpty()) {
            throw new BadRequestException("순서를 변경할 메뉴가 적어도 한개 이상 필요합니다.");
        }

        List<Menu> menuList = menuRepository.findAllByParentId(menuUpdateFormList.get( 0 ).getParentId());

        for ( MenuUpdateForm menuUpdateForm: menuUpdateFormList ) {
            Menu menu = menuList.stream().filter( menus -> menus.getId().equals( menuUpdateForm.getMenuId() ) ).findFirst().orElseThrow();
            menu.updateOrder(menuUpdateForm.getOrderNumber());
        }
        return menuUpdateFormList;
    }

}
