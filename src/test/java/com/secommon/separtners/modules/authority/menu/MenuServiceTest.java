package com.secommon.separtners.modules.authority.menu;

import com.secommon.separtners.infra.AbstractContainerBaseTest;
import com.secommon.separtners.infra.MockMvcTest;
import com.secommon.separtners.modules.authority.menu.form.MenuForm;
import com.secommon.separtners.modules.authority.menu.form.MenuUpdateForm;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@MockMvcTest
class MenuServiceTest extends AbstractContainerBaseTest {

    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuRepository menuRepository;


    @BeforeEach
    void beforeEach() {
        saveFirstMenu();
    }

    @Test
    @DisplayName("메뉴 등록 테스트")
    void registerMenu_success () throws Exception {
        Menu menu = menuRepository.findAll().stream().findFirst().orElseThrow();
        // given
        String menuName = "첫번째 메뉴";
        MenuForm menuForm = MenuForm.builder()
                .menuName(menuName)
                .page(false)
                .parentId(menu.getId())
                .build();
        // when
        Long savedMenuId = menuService.saveMenu(menuForm);
        log.error(" savedId :: {}", savedMenuId);
        // then
        Optional<Menu> byId = menuRepository.findById(savedMenuId);
        assertTrue(byId.isPresent());
        assertEquals(menuName, byId.get().getMenuName());
        assertFalse(byId.get().isPage());
    }


    @Test
    @DisplayName("메뉴 등록- 실패")
    void registerMenu_fail () throws Exception {
        // given
        MenuForm menuForm = MenuForm.builder()
                .menuName("zzzz")
                .build();
        // when

        assertThrows(InvalidDataAccessApiUsageException.class, ()-> {
           menuService.saveMenu(menuForm);
        });

    }

    private void saveFirstMenu() {
        Menu menu = Menu.builder()
                .orderNumber(1)
                .menuName("최초 메뉴")
                .active(true)
                .build();
        menuRepository.save(menu);
    }


    @Test
    @DisplayName("메뉴 변경 - 성공")
    void changeMenu_success() throws Exception {
        Optional<Menu> first = menuRepository.findAll().stream().findFirst();
        assertTrue(first.isPresent());
        // given
        String menuName = "첫번째 메뉴";
        MenuForm menuForm = MenuForm.builder()
                .menuName(menuName)
                .page(false)
                .parentId(first.get().getId())
                .build();
        // when
        Long savedMenuId = menuService.saveMenu(menuForm);
        String changedMenuName = "변경된메뉴이름";
        MenuUpdateForm menuUpdateForm = MenuUpdateForm.builder()
                .menuId(savedMenuId)
                .menuName(changedMenuName)
                .parentId(first.get().getId())
                .build();
        // when
        Long changedMenuId = menuService.updateMenu(menuUpdateForm);
        Optional<Menu> byId = menuRepository.findById(changedMenuId);
        // then
        assertTrue(byId.isPresent());
        assertEquals(changedMenuName, byId.get().getMenuName());
        assertNotEquals(menuName, byId.get().getMenuName());

    }


    @Test
    @DisplayName("순서 변경")
    void changeOrderNumber_success() throws Exception {
        Optional<Menu> first = menuRepository.findAll().stream().findFirst();
        assertTrue(first.isPresent());
        // given
        String menuName = "첫번째 메뉴";
        MenuForm menuForm = saveMenu(first.get(), 1, menuName);
        Long savedMenuId = menuService.saveMenu(menuForm);


        MenuUpdateForm menuUpdateForm = updateMenuForm(first.get(), menuName, 2, savedMenuId);
        String menuName2 = "변경된메뉴이름";
        MenuForm menuForm2 = saveMenu(first.get(), 2, menuName2);

        Long savedMenuId2 = menuService.saveMenu(menuForm2);
        MenuUpdateForm menuUpdateForm2 = updateMenuForm(first.get(), menuName2, 1, savedMenuId2);
        // when
        List<MenuUpdateForm> menuUpdateFormList = new ArrayList<>();
        menuUpdateFormList.add(menuUpdateForm);
        menuUpdateFormList.add(menuUpdateForm2);
        menuService.updateOrder(menuUpdateFormList);
        // then
        List<Menu> menuList = menuRepository.findAllByParentId(1L);
        for (Menu menu : menuList) {
            if(menu.getId().equals(savedMenuId)) {
                assertEquals(2, menu.getOrderNumber());
            } else if(menu.getId().equals(savedMenuId2)) {
                assertEquals(1, menu.getOrderNumber());
            }
        }


    }

    private MenuUpdateForm updateMenuForm(Menu first, String menuName, int orderNumber, Long savedMenuId) {
        return MenuUpdateForm
                .builder()
                .menuId(savedMenuId)
                .menuName(menuName)
                .parentId(first.getId())
                .orderNumber(orderNumber)
                .build();
    }

    private MenuForm saveMenu(Menu first, int orderNumber, String menuName) {
        return MenuForm.builder()
                .menuName(menuName)
                .page(false)
                .orderNumber(orderNumber)
                .parentId(first.getId())
                .build();
    }
}