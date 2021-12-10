package com.secommon.separtners.modules.account.repository;

import com.secommon.separtners.modules.account.dto.AccountWorkDto;

public interface AccountRepositoryQuerydsl {

    AccountWorkDto findAllWithWorkGroupAndWorkAndTimes(Long employeeId);

}
