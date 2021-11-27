package com.secommon.separtners.modules.flexiblework.flexiblework.repository;

import com.secommon.separtners.modules.flexiblework.flexiblework.FlexibleWork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlexibleWorkRepository extends JpaRepository<FlexibleWork, Long>, FlexibleWorkRepositoryQuerydsl {

}
