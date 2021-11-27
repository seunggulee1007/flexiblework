package com.secommon.separtners.modules.flexiblework.flexiblework.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.secommon.separtners.modules.common.Querydsl4RepositorySupport;
import com.secommon.separtners.modules.flexiblework.flexiblework.FlexibleWork;
import com.secommon.separtners.modules.flexiblework.flexiblework.dto.FlexibleWorkDto;
import com.secommon.separtners.modules.flexiblework.flexiblework.enums.FlexibleWorkType;
import com.secommon.separtners.modules.flexiblework.flexiblework.form.FlexibleWorkSearchForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import static com.querydsl.core.types.Projections.constructor;
import static com.secommon.separtners.modules.flexiblework.flexiblework.QFlexibleWork.flexibleWork;

public class FlexibleWorkRepositoryImpl extends Querydsl4RepositorySupport implements FlexibleWorkRepositoryQuerydsl {

    public FlexibleWorkRepositoryImpl() {
        super(FlexibleWork.class);
    }

    @Override
    public Page<FlexibleWorkDto> findAllBySearchForm ( FlexibleWorkSearchForm searchForm, Pageable pageable ) {
        return applyPagination( pageable, query -> query.select(
                constructor( FlexibleWorkDto.class, flexibleWork) )
                .from( flexibleWork )
                .where(
                    workTypeEq(searchForm.getFlexibleWorkType()),
                    workNameLike(searchForm.getFlexibleWorkName())
                )
        );
    }

    private BooleanExpression workNameLike ( String flexibleWorkName ) {
        return StringUtils.hasText( flexibleWorkName ) ? flexibleWork.flexibleWorkName.like( "%"+ flexibleWorkName + "%" ) : null;
    }

    private BooleanExpression workTypeEq ( String flexibleWorkType ) {
        return StringUtils.hasText(flexibleWorkType) ? flexibleWork.flexibleWorkType.eq( FlexibleWorkType.valueOf( flexibleWorkType ) ) : null;
    }

}
