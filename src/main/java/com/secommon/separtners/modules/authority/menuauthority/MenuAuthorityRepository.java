package com.secommon.separtners.modules.authority.menuauthority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MenuAuthorityRepository extends JpaRepository<MenuAuthority, Long>, MenuAuthorityRepositoryQuerydsl {
}
