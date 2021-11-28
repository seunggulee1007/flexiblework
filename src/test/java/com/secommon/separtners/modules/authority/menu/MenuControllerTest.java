package com.secommon.separtners.modules.authority.menu;

import com.secommon.separtners.infra.AbstractContainerBaseTest;
import com.secommon.separtners.infra.MockMvcTest;
import com.secommon.separtners.infra.security.WithMockJwtAuthentication;
import com.secommon.separtners.modules.authority.menu.form.MenuForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
class MenuControllerTest extends AbstractContainerBaseTest {

    @Autowired
    private MenuRepository menuRepository;

    @BeforeEach
    void beforeEach() {
        saveFirstMenu();
    }

    @Test
    @DisplayName("메뉴 등록 테스트 - 성공")
    @WithMockJwtAuthentication
    void registerMenu_success () throws Exception {
        // given
        MenuForm menuForm = MenuForm.builder()
                .menuName("firstMenu")
                .active(true)
                .parentId(1L)
                .orderNumber(1)
            .build();
        // when
        this.mockMvc.perform(post("/api/menu")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(menuForm))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true))
                .andExpect(jsonPath("response").isNumber())

                ;
        // then

    }

    private void saveFirstMenu() {
        Menu menu = Menu.builder()
                .orderNumber(1)
                .menuName("최초 메뉴")
                .active(true)
                .build();
        menuRepository.save(menu);
    }
}