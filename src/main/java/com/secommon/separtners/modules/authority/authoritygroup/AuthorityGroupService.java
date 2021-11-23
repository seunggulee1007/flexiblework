package com.secommon.separtners.modules.authority.authoritygroup;

import com.secommon.separtners.infra.commons.BaseServiceAnnotation;
import com.secommon.separtners.modules.account.Account;
import com.secommon.separtners.modules.account.repository.AccountRepository;
import com.secommon.separtners.modules.authority.authoritygroup.form.AuthorityGroupForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
        if(authorityGroupForm.getAuthorityGroupId() != null) {
            AuthorityGroup authorityGroup = authorityGroupRepository.findById( authorityGroupForm.getAuthorityGroupId() ).orElseThrow();
            authorityGroup.updateByAuthorityGroupForm(authorityGroupForm);
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

}
