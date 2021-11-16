package com.secommon.separtners.modules.flexiblework.repository;

import com.secommon.separtners.modules.flexiblework.MandatoryTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MandatoryTimeRepository extends JpaRepository<MandatoryTime, Long> {
}
