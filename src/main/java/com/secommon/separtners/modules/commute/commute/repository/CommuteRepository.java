package com.secommon.separtners.modules.commute.commute.repository;

import com.secommon.separtners.modules.commute.commute.Commute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommuteRepository extends JpaRepository<Commute, Long>, CommuteRepositoryQuerdsl {
}
