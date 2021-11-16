package com.secommon.separtners.modules.commute;

import java.util.Optional;

public interface CommuteRepositoryQuerdsl {

    Optional<CommuteDto> findByAccountIdAndWorkedDateBetween(CommuteSearchForm commuteSearchForm);

    Optional<Commute> findByAccountIdAndWorkedDateBetween(Long accountId);

}
