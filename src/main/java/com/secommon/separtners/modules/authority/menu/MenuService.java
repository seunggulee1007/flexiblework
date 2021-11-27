package com.secommon.separtners.modules.authority.menu;

import com.secommon.separtners.infra.advice.exceptions.BadRequestException;
import com.secommon.separtners.infra.commons.BaseServiceAnnotation;
import com.secommon.separtners.modules.authority.menu.form.MenuForm;
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

    public Long saveNewMenu( MenuForm menuForm) {
        if(menuForm.getMenuId() != null) {
            return saveMenu( menuForm );
        } else {
            return updateMenu( menuForm );
        }
    }

    private Long saveMenu ( MenuForm menuForm ) {
        Menu menu = Menu.builder()
                .menuName( menuForm.getMenuName() )
                .active( menuForm.isActive() )
                .orderNumber( menuForm.getOrder() )
                .page( menuForm.isPage() )
                .menuPath( menuForm.getMenuPath() )
                .build();
        Menu parent = menuRepository.findById( menuForm.getParentId() ).orElseThrow();
        menuRepository.save( menu );
        menu.updateParent( parent );
        return menu.getId();
    }

    private Long updateMenu ( MenuForm menuForm ) {
        Menu menu = menuRepository.findById( menuForm.getMenuId() ).orElseThrow();
        menu.updateMenu( menuForm );
        if( !menu.getParent().getId().equals( menuForm.getParentId() ) ) {
            Menu parent = menuRepository.findById( menuForm.getParentId() ).orElseThrow();
            menu.updateParent(parent);
        }
        return menu.getId();
    }

    public void updateOrder(List<MenuForm> menuFormList) {

        if(menuFormList.isEmpty()) {
            throw new BadRequestException("순서를 변경할 메뉴가 적어도 한개 이상 필요합니다.");
        }

        List<Menu> menuList = menuRepository.findAllByParentId(menuFormList.get( 0 ).getParentId());

        for ( MenuForm menuForm: menuFormList ) {
            Menu menu = menuList.stream().filter( menus -> menus.getId().equals( menuForm.getMenuId() ) ).findFirst().orElseThrow();
            menu.updateOrder(menuForm.getOrder());
        }

    }

}
