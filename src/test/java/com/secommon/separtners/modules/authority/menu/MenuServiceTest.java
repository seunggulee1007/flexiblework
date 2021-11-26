package com.secommon.separtners.modules.authority.menu;

import com.secommon.separtners.infra.AbstractContainerBaseTest;
import com.secommon.separtners.infra.MockMvcTest;
import com.secommon.separtners.modules.authority.menu.form.MenuForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@MockMvcTest
class MenuServiceTest extends AbstractContainerBaseTest {

    @Autowired
    private MenuService menuService;


    @Test
    @DisplayName("메뉴 등록 테스트")
    void registerMenu_success () throws Exception {
        // given
        MenuForm menuForm = MenuForm.builder()
                .menuName( "첫번째 메뉴" )
                .build();
        // when

        // then

    }
}