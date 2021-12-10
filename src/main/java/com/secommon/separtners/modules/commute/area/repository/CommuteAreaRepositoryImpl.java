package com.secommon.separtners.modules.commute.area.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.secommon.separtners.modules.common.Querydsl4RepositorySupport;
import com.secommon.separtners.modules.commute.area.CommuteArea;
import com.secommon.separtners.modules.commute.area.CommuteAreaDto;
import com.secommon.separtners.modules.commute.area.form.CommuteAreaSearchForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.querydsl.core.types.Projections.constructor;
import static com.secommon.separtners.modules.commute.area.QCommuteArea.commuteArea;

public class CommuteAreaRepositoryImpl extends Querydsl4RepositorySupport implements CommuteAreaRepositoryQuerydsl {
    public CommuteAreaRepositoryImpl() {
        super(CommuteArea.class);
    }

    @Override
    public Page<CommuteAreaDto> findAllByCommuteAreaSearchForm(CommuteAreaSearchForm searchForm, Pageable pageable) {
        return applyPagination(pageable, query -> query.select(constructor(CommuteAreaDto.class, commuteArea))
                .from(commuteArea)
                .where(
                        searchConditionLike(searchForm)
                )
        );
    }

    private BooleanExpression searchConditionLike(CommuteAreaSearchForm searchForm) {
        if(searchForm.getSearchKind() == 1) {
            return commuteArea.areaName.like("%"+searchForm.getSearchKeyword()+"%");
        } else if (searchForm.getSearchKind() == 2) {
            return commuteArea.address.roadName.like("%"+ searchForm.getSearchKeyword() + "%");
        }
        return null;
    }

}
