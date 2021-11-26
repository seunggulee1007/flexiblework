package com.secommon.separtners.modules.authority.menu;

import com.secommon.separtners.modules.common.Querydsl4RepositorySupport;

public class MenuRepositoryImpl extends Querydsl4RepositorySupport implements MenuRepositoryQuerydsl {

    public MenuRepositoryImpl () {
        super( Menu.class );
    }


}
