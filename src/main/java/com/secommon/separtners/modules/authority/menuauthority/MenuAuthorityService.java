package com.secommon.separtners.modules.authority.menuauthority;

import com.secommon.separtners.infra.commons.BaseServiceAnnotation;
import com.secommon.separtners.modules.authority.authoritygroup.AuthorityGroup;
import com.secommon.separtners.modules.authority.authoritygroup.AuthorityGroupRepository;
import com.secommon.separtners.modules.authority.menu.Menu;
import com.secommon.separtners.modules.authority.menu.MenuRepository;
import com.secommon.separtners.modules.authority.menuauthority.form.MenuAuthorityForm;
import com.secommon.separtners.modules.authority.menuauthority.form.MenuAuthorityUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@BaseServiceAnnotation
@RequiredArgsConstructor
public class MenuAuthorityService {

    private final MenuAuthorityRepository menuAuthorityRepository;
    private final MenuRepository menuRepository;
    private final AuthorityGroupRepository authorityGroupRepository;

    public Long saveMenuAuthority(MenuAuthorityForm menuAuthorityForm) {
        Menu menu = menuRepository.findById(menuAuthorityForm.getMenuId()).orElseThrow();
        AuthorityGroup authorityGroup = authorityGroupRepository.findById(menuAuthorityForm.getAuthorityGroupId()).orElseThrow();
        MenuAuthority menuAuthority = MenuAuthority.builder()
                .menu(menu)
                .authorityGroup(authorityGroup)
                .menuRole(menuAuthorityForm.getMenuRole())
                .build();
        menuAuthorityRepository.save(menuAuthority);
        return menuAuthority.getId();
    }

    public Long updateMenuAuthority(MenuAuthorityUpdateForm menuAuthorityUpdateForm) {
        MenuAuthority menuAuthority = menuAuthorityRepository.findById(menuAuthorityUpdateForm.getMenuAuthorityId()).orElseThrow();
        if(!menuAuthority.getMenu().getId().equals(menuAuthorityUpdateForm.getMenuId())) {
            Menu menu = menuRepository.findById(menuAuthorityUpdateForm.getMenuId()).orElseThrow();
            menuAuthority.changeMenu(menu);
        }
        if(!menuAuthority.getAuthorityGroup().getId().equals(menuAuthorityUpdateForm.getAuthorityGroupId())) {
            AuthorityGroup authorityGroup = authorityGroupRepository.findById(menuAuthorityUpdateForm.getAuthorityGroupId()).orElseThrow();
            menuAuthority.changeAuthorityGroup(authorityGroup);
        }
        return menuAuthority.getId();
    }

    public List<MenuAuthorityDto> findAllByMenuId(Long menuId) {
        return menuAuthorityRepository.findMenuAuthorityByMenuId(menuId);
    }

}
