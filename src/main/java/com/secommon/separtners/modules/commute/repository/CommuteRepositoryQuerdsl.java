package com.secommon.separtners.modules.commute.repository;

import com.secommon.separtners.modules.commute.Commute;
import com.secommon.separtners.modules.commute.CommuteDto;
import com.secommon.separtners.modules.commute.form.CommuteSearchForm;

import java.util.Optional;

public interface CommuteRepositoryQuerdsl {

    Optional<CommuteDto> findByAccountIdAndWorkedDateBetween(CommuteSearchForm commuteSearchForm);

    Optional<Commute> findByAccountIdAndWorkedDateBetween(Long accountId);

}
