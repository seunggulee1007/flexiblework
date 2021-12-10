package com.secommon.separtners.modules.commute.commute.repository;

import com.secommon.separtners.modules.commute.commute.Commute;
import com.secommon.separtners.modules.commute.commute.CommuteDto;
import com.secommon.separtners.modules.commute.commute.form.CommuteSearchForm;

import java.util.Optional;

public interface CommuteRepositoryQuerdsl {

    Optional<CommuteDto> findByAccountIdAndWorkedDateBetween(CommuteSearchForm commuteSearchForm);

    Optional<Commute> findByAccountIdAndWorkedDateBetween(Long accountId);

}
