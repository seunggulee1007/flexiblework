package com.secommon.separtners.modules.authority.authoritygroup;

import com.secommon.separtners.infra.commons.BaseServiceAnnotation;
import com.secommon.separtners.modules.account.Account;
import com.secommon.separtners.modules.account.repository.AccountRepository;
import com.secommon.separtners.modules.authority.authoritygroup.form.AuthorityGroupForm;
import com.secommon.separtners.modules.authority.authoritygroup.form.AuthorityGroupUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@BaseServiceAnnotation
@RequiredArgsConstructor
public class AuthorityGroupService {

    private final AuthorityGroupRepository authorityGroupRepository;
    private final AccountRepository accountRepository;
    private final AuthorityGroupAccountRepository authorityGroupAccountRepository;

    public void saveAuthorityGroup( AuthorityGroupForm authorityGroupForm) {
        AuthorityGroup authorityGroup = AuthorityGroup.builder()
                .authorityGroupName(authorityGroupForm.getAuthorityGroupName())
                .active(authorityGroupForm.isActive())
                .admin(authorityGroupForm.isAdmin())
                .basic(authorityGroupForm.isBasic())
            .build();
        authorityGroupRepository.save(authorityGroup);
        List<AuthorityGroupAccount> authorityGroupAccountList = new ArrayList<>();
        List<Long> accountIds = authorityGroupForm.getAccountIds();
        for (Long accountId : accountIds) {
            Account account = accountRepository.findById(accountId).orElseThrow();
            AuthorityGroupAccount authorityGroupAccount = AuthorityGroupAccount.builder()
                    .authorityGroup(authorityGroup)
                    .account(account)
                    .build();
            authorityGroupAccountRepository.save(authorityGroupAccount);
            authorityGroupAccount.setGroupAccount(account, authorityGroup);
        }
    }

    public void updateAuthorityGroup(AuthorityGroupUpdateForm authorityGroupUpdateForm ) {
        AuthorityGroup authorityGroup = authorityGroupRepository.findById( authorityGroupUpdateForm.getAuthorityGroupId() ).orElseThrow();
        authorityGroup.updateByAuthorityGroupUpdateForm(authorityGroupUpdateForm);
        List<AuthorityGroupAccount> byAuthorityGroup = authorityGroupAccountRepository.findByAuthorityGroup( authorityGroup );
        List<Long> savedAccountIds = byAuthorityGroup.stream().map( authorityGroupAccount ->
                authorityGroupAccount.getAccount().getId() ).collect( Collectors.toList() );

        List<Long> compareList = savedAccountIds.stream().filter( compare ->
                        authorityGroup.getAuthorityGroupAccountList().stream().map(authorityGroupAccount ->
                                        authorityGroupAccount.getAccount().getId())
                                .noneMatch( Predicate.isEqual( compare ) ))
                .collect( Collectors.toList());
        for ( Long accountId: compareList ) {
            Account account = accountRepository.findById( accountId ).orElseThrow();
            account.removeAuthorityGroup(authorityGroup);
        }
    }

}
