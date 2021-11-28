package com.secommon.separtners.modules.authority.menuauthority;

import com.secommon.separtners.modules.common.Querydsl4RepositorySupport;

import java.util.List;

import static com.secommon.separtners.modules.authority.authoritygroup.QAuthorityGroup.authorityGroup;
import static com.secommon.separtners.modules.authority.menuauthority.QMenuAuthority.menuAuthority;

public class MenuAuthorityRepositoryImpl extends Querydsl4RepositorySupport implements MenuAuthorityRepositoryQuerydsl {

    public MenuAuthorityRepositoryImpl() {
        super(MenuAuthority.class);
    }

    public List<MenuAuthorityDto> findMenuAuthorityByMenuId(Long menuId) {
        return select( new QMenuAuthorityDto(authorityGroup.id, authorityGroup.authorityGroupName, menuAuthority.menu.id, menuAuthority.menuRole))
                .from(authorityGroup)
                .leftJoin(authorityGroup.menuAuthorityList, menuAuthority)
                .on(menuAuthority.menu.id.eq(menuId))
                .where(authorityGroup.active.isTrue())
                .fetch();
    }

}
