package com.secommon.separtners.modules.authority.authoritygroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface AuthorityGroupAccountRepository extends JpaRepository<AuthorityGroupAccount, Long> {

    List<AuthorityGroupAccount> findByAuthorityGroup(AuthorityGroup authorityGroup);

}
