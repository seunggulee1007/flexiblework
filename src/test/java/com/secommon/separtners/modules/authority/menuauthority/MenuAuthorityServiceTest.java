package com.secommon.separtners.modules.authority.menuauthority;

import com.secommon.separtners.infra.AbstractContainerBaseTest;
import com.secommon.separtners.infra.MockMvcTest;
import com.secommon.separtners.modules.authority.authoritygroup.AuthorityGroup;
import com.secommon.separtners.modules.authority.authoritygroup.AuthorityGroupRepository;
import com.secommon.separtners.modules.authority.menu.Menu;
import com.secommon.separtners.modules.authority.menu.MenuRepository;
import com.secommon.separtners.modules.authority.menuauthority.form.MenuAuthorityForm;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
@MockMvcTest
class MenuAuthorityServiceTest extends AbstractContainerBaseTest {

    @Autowired
    private MenuAuthorityService menuAuthorityService;
    @Autowired
    private AuthorityGroupRepository authorityGroupRepository;
    @Autowired
    private MenuRepository menuRepository;

    @Test
    @DisplayName("메뉴 아이디로 그룹 리스트 찾기")
    void findAllByMenuId () throws Exception {
        // given
        saveMenu("menu1");
        saveMenu("menu2");
        saveGroup("group1");
        saveGroup("group2");
        saveGroup("group3");
        // when
        List<MenuAuthorityDto> allByMenuId = menuAuthorityService.findAllByMenuId(1L);
        // then
        for (MenuAuthorityDto menuAuthorityDto : allByMenuId) {
            log.error("menuAuthorityDto :: {}", menuAuthorityDto.toString() );
        }

    }

    
    
    @Test
    @DisplayName("그룹별 권한 등록")
    void groupAuthority() throws Exception {
        // given
        Menu menu = saveMenu("menu1");
        saveMenu("menu2");
        AuthorityGroup authorityGroup = saveGroup("group1");
        saveGroup("group2");
        saveGroup("group3");
        MenuAuthorityForm menuAuthorityForm = MenuAuthorityForm.builder()
                .menuId(menu.getId())
                .authorityGroupId(authorityGroup.getId())
                .menuRole(MenuRole.WRITE)
                .build();
        // when
        menuAuthorityService.saveMenuAuthority(menuAuthorityForm);
        // then
        List<MenuAuthorityDto> allByMenuId = menuAuthorityService.findAllByMenuId(menu.getId());
        for (MenuAuthorityDto menuAuthorityDto : allByMenuId) {
            log.error("menuAuthorityDto :: {}", menuAuthorityDto.toString() );
            if(menu.getId().equals(menuAuthorityDto.getMenuId()) && authorityGroup.getId().equals(menuAuthorityDto.getAuthorityGroupId())) {
                assertEquals(MenuRole.WRITE, menuAuthorityDto.getMenuRole());
            } else {
                assertNull(menuAuthorityDto.getMenuRole());
            }
        }
    }
    private AuthorityGroup saveGroup(String groupName) {

        AuthorityGroup authorityGroup = AuthorityGroup.builder()
                .authorityGroupName(groupName)
                .active(true)
                .build();
        authorityGroupRepository.save(authorityGroup);
        return authorityGroup;
    }

    private Menu saveMenu(String menuName) {
        Menu menu = Menu.builder()
                .menuName(menuName)
                .build();
        menuRepository.save(menu);
        return menu;
    }
}