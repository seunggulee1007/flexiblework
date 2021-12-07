package com.secommon.separtners.modules.commute.area.repository;

import com.secommon.separtners.modules.commute.area.CommuteArea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommuteAreaRepository extends JpaRepository<CommuteArea, Long>, CommuteAreaRepositoryQuerydsl {
}
