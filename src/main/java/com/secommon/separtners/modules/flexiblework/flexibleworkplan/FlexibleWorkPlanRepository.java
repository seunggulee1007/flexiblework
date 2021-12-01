package com.secommon.separtners.modules.flexiblework.flexibleworkplan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface FlexibleWorkPlanRepository extends JpaRepository<FlexibleWorkPlan, Long> {
}
