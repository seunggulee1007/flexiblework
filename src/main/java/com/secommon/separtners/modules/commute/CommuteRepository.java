package com.secommon.separtners.modules.commute;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommuteRepository extends JpaRepository<Commute, Long>, CommuteRepositoryQuerdsl {
}
