package com.secommon.separtners.modules.commute.group;

import com.secommon.separtners.infra.commons.BaseServiceAnnotation;
import com.secommon.separtners.modules.account.Account;
import com.secommon.separtners.modules.account.repository.AccountRepository;
import com.secommon.separtners.modules.commute.group.form.CommuteGroupForm;
import lombok.RequiredArgsConstructor;

import java.util.List;

@BaseServiceAnnotation
@RequiredArgsConstructor
public class CommuteGroupService {

    private final CommuteGroupRepository commuteGroupRepository;
    private final AccountRepository accountRepository;

    public void saveCommuteGroup(CommuteGroupForm commuteGroupForm) {
        CommuteGroup commuteGroup = CommuteGroup.builder()
                .groupName(commuteGroupForm.getGroupName())
                .active(commuteGroupForm.isActive())
            .build();
        commuteGroupRepository.save(commuteGroup);
        List<Long> accountIdList = commuteGroupForm.getAccountIdList();
        for (Long accountId : accountIdList) {
            Account account = accountRepository.findById(accountId).orElseThrow();
            account.setCommuteGroup(commuteGroup);
        }
    }

}
