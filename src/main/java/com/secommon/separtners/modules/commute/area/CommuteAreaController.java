package com.secommon.separtners.modules.commute.area;

import com.secommon.separtners.infra.commons.BaseAnnotation;
import com.secommon.separtners.modules.commute.area.form.CommuteAreaForm;
import com.secommon.separtners.modules.commute.area.form.CommuteAreaSearchForm;
import com.secommon.separtners.modules.commute.area.form.CommuteAreaUpdateForm;
import com.secommon.separtners.utils.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static com.secommon.separtners.utils.ApiUtil.success;

@BaseAnnotation
@RequiredArgsConstructor
public class CommuteAreaController {

    private final CommuteAreaService commuteAreaService;

    private static final String BASE_URL = "/commute/area";

    @GetMapping(BASE_URL + "/id/{commuteAreaId}")
    public ApiUtil.ApiResult<CommuteAreaDto> findOneById(@PathVariable Long commuteAreaId) {
        return success(commuteAreaService.findOneById(commuteAreaId));
    }

    @GetMapping(BASE_URL + "/list")
    public ApiUtil.ApiResult<Page<CommuteAreaDto>> findAllByCommuteAreaSearchForm(CommuteAreaSearchForm searchForm, Pageable pageable) {
        return success(commuteAreaService.findAllByCommuteAreaSearchForm(searchForm, pageable));
    }

    @PostMapping(BASE_URL)
    public ApiUtil.ApiResult<Long> saveCommuteArea(@RequestBody CommuteAreaForm commuteAreaForm) {
        return success(commuteAreaService.saveCommuteArea(commuteAreaForm), "등록되었습니다.");
    }

    @PutMapping(BASE_URL)
    public ApiUtil.ApiResult<Long> updateCommuteArea(@RequestBody CommuteAreaUpdateForm commuteAreaUpdateForm) {
        return success(commuteAreaService.modifyCommuteArea(commuteAreaUpdateForm), "수정되었습니다.");
    }

}
