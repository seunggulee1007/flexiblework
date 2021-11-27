package com.secommon.separtners.modules.authority.authoritygroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface AuthorityGroupRepository extends JpaRepository<AuthorityGroup, Long> {
}
