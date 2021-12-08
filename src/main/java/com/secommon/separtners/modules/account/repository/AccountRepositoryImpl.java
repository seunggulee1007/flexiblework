package com.secommon.separtners.modules.account.repository;

import com.secommon.separtners.modules.account.Account;
import com.secommon.separtners.modules.account.QAccount;
import com.secommon.separtners.modules.common.Querydsl4RepositorySupport;
import com.secommon.separtners.modules.company.employee.dto.AccountWorkDto;

import static com.querydsl.core.types.Projections.constructor;
import static com.secommon.separtners.modules.account.QAccount.account;
import static com.secommon.separtners.modules.flexiblework.flexiblework.QFlexibleWork.flexibleWork;
import static com.secommon.separtners.modules.flexiblework.flexibleworkgroup.QFlexibleWorkGroup.flexibleWorkGroup;

public class AccountRepositoryImpl extends Querydsl4RepositorySupport implements AccountRepositoryQuerydsl {

    public AccountRepositoryImpl() {
        super( Account.class);
    }

    QAccount subAccount = new QAccount( "subAccount" );

    public AccountWorkDto findAllWithWorkGroupAndWorkAndTimes(Long accountId) {
        return select(
                constructor(AccountWorkDto.class, account, flexibleWork, flexibleWorkGroup))
                .from(account)
                .leftJoin(account.flexibleWorkGroup, flexibleWorkGroup)
                .leftJoin(flexibleWorkGroup.flexibleWork, flexibleWork)
                .where( account.id.eq(accountId)).fetchOne();
    }

}
