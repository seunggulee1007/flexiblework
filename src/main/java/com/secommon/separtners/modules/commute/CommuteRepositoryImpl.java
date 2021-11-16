package com.secommon.separtners.modules.commute;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.secommon.separtners.modules.common.Querydsl4RepositorySupport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static com.querydsl.core.types.Projections.constructor;
import static com.secommon.separtners.modules.commute.QCommute.commute;

public class CommuteRepositoryImpl extends Querydsl4RepositorySupport implements CommuteRepositoryQuerdsl  {

    public CommuteRepositoryImpl() {
        super(Commute.class);
    }

    @Override
    public Optional<CommuteDto> findByAccountIdAndWorkedDateBetween( CommuteSearchForm commuteSearchForm) {
        return Optional.ofNullable(select( constructor( CommuteDto.class, commute ) )
                .from( commute )
                .where(
                        commute.account.id.eq( commuteSearchForm.getAccountId() ),
                        betweenCreatedDate( commuteSearchForm.getWorkedDate() )
                )
                .fetchOne());
    }

    private BooleanExpression betweenCreatedDate( LocalDateTime workedDate ) {
        if(workedDate == null) {
            workedDate = LocalDateTime.now();
        }
        LocalTime midNight = LocalTime.MIDNIGHT;
        LocalDate today = workedDate.toLocalDate();
        LocalDateTime todayMidNight = LocalDateTime.of( today, midNight );
        return commute.createdDate.between( todayMidNight, todayMidNight.plusDays( 1 ).minusSeconds( 1L ));
    }

    public Optional<Commute> findByAccountIdAndWorkedDateBetween(Long accountId) {
        return Optional.ofNullable(select( commute )
                .from( commute )
                .where(
                        commute.account.id.eq( accountId ),
                        betweenCreatedDate( null )
                )
                .fetchOne());
    }

}
