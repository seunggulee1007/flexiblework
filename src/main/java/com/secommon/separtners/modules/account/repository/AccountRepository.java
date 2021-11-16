package com.secommon.separtners.modules.account.repository;

import com.secommon.separtners.modules.account.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUserName ( String nickname );

    @EntityGraph("Account.withAccountRolesAndEmployee")
    Optional<Account> findByEmail ( String email );

    boolean existsByEmail ( String email );

}
