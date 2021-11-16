package com.secommon.separtners.modules.account.repository;

import com.secommon.separtners.modules.account.Account;
import com.secommon.separtners.modules.account.QAccount;
import com.secommon.separtners.modules.common.Querydsl4RepositorySupport;

public class AccountRepositoryImpl extends Querydsl4RepositorySupport implements AccountRepositoryQuerydsl {

    public AccountRepositoryImpl() {
        super( Account.class);
    }

    QAccount subAccount = new QAccount( "subAccount" );


}
