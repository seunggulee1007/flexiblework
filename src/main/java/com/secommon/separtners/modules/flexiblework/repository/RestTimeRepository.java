package com.secommon.separtners.modules.flexiblework.repository;

import com.secommon.separtners.modules.flexiblework.RestTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RestTimeRepository extends JpaRepository<RestTime, Long> {
}
