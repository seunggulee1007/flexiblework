package com.secommon.separtners.modules.commute.area.repository;

import com.secommon.separtners.modules.commute.area.CommuteAreaDto;
import com.secommon.separtners.modules.commute.area.form.CommuteAreaSearchForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CommuteAreaRepositoryQuerydsl {

    Page<CommuteAreaDto> findAllByCommuteAreaSearchForm(CommuteAreaSearchForm searchForm, Pageable pageable);

}
