package com.secommon.separtners.modules.flexiblework.flexiblework.repository;

import com.secommon.separtners.modules.flexiblework.flexiblework.dto.FlexibleWorkDto;
import com.secommon.separtners.modules.flexiblework.flexiblework.form.FlexibleWorkSearchForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FlexibleWorkRepositoryQuerydsl {

    Page<FlexibleWorkDto> findAllBySearchForm( FlexibleWorkSearchForm searchForm, Pageable pageable );

}
