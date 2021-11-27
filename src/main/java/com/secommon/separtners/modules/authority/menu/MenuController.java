package com.secommon.separtners.modules.authority.menu;

import com.secommon.separtners.infra.commons.BaseAnnotation;
import com.secommon.separtners.modules.authority.menu.form.MenuForm;
import com.secommon.separtners.utils.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

import static com.secommon.separtners.utils.ApiUtil.success;

@BaseAnnotation
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/menu/list")
    public ApiUtil.ApiResult<List<MenuDto>> findAllMenuList() {
        return success(menuService.findAllMenu());
    }

    @PostMapping("/menu")
    public ApiUtil.ApiResult<Long> saveMenu(@Valid @RequestBody MenuForm menuForm) {
        return success(menuService.saveMenu(menuForm), "등록되었습니다.");
    }

    @PutMapping("/menu")
    public ApiUtil.ApiResult<Long> updateMenu(@Valid @RequestBody MenuForm menuForm) {
        return success(menuService.saveMenu(menuForm), "수정되었습니다.");
    }

    @PutMapping("/menu/ordernumber")
    public ApiUtil.ApiResult<List<MenuForm>> updateMenuOrderNumber(@Valid List<MenuForm> menuFormList) {
        return success(menuService.updateOrder(menuFormList));
    }

}
