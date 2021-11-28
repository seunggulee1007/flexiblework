package com.secommon.separtners.modules.authority.menuauthority;

import java.util.List;

public interface MenuAuthorityRepositoryQuerydsl {
    List<MenuAuthorityDto> findMenuAuthorityByMenuId(Long menuId);
}
