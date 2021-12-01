package com.secommon.separtners.modules.commute.repository;

import com.secommon.separtners.modules.commute.Commute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommuteRepository extends JpaRepository<Commute, Long>, CommuteRepositoryQuerdsl {
}
