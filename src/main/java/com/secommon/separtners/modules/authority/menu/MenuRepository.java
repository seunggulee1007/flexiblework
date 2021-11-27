package com.secommon.separtners.modules.authority.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Long>{

    List<Menu> findAllByParentId ( Long parentId );

}
