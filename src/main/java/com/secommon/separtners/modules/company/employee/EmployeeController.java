package com.secommon.separtners.modules.company.employee;

import com.secommon.separtners.infra.commons.BaseAnnotation;
import com.secommon.separtners.modules.common.CommonMessage;
import com.secommon.separtners.modules.common.EnumMapperValue;
import com.secommon.separtners.modules.company.employee.dto.EmployeeDto;
import com.secommon.separtners.modules.company.employee.enums.EmployeeStatus;
import com.secommon.separtners.modules.company.employee.enums.Position;
import com.secommon.separtners.modules.company.employee.form.EmployeeSearchForm;
import com.secommon.separtners.modules.company.employee.form.PositionForm;
import com.secommon.separtners.modules.company.employee.form.StatusForm;
import com.secommon.separtners.utils.ApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.secommon.separtners.utils.ApiUtil.success;

@Slf4j
@BaseAnnotation
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    private static final String PREFIX_URL = "/employee";
    /**
     * 직급 리스트 조회
     */
    @GetMapping(PREFIX_URL+"/positions")
    public ApiUtil.ApiResult<List<EnumMapperValue>> getPositions() {
        return success( Arrays.stream(Position.values())
                .map( EnumMapperValue::new )
                .collect( Collectors.toList())
        );
    }

    /**
     * 직급 수정
     * @param positionForm : 수정 폼
     * @return Position : 변경된 직급
     */
    @PutMapping(PREFIX_URL+"/position")
    public ApiUtil.ApiResult<Position> updatePosition(@Valid @RequestBody PositionForm positionForm ) {
        return success( employeeService.updatePosition( positionForm ), CommonMessage.SUCCESS_UPDATE.getMessage() );
    }

    /**
     * 상태 수정
     * @param statusForm : 상태 수정 폼
     * @return EmployeeStatus : 변경된 상태
     */
    @PutMapping(PREFIX_URL+"/status")
    public ApiUtil.ApiResult<EmployeeStatus> updateStatus(@Valid @RequestBody StatusForm statusForm ) {
        return success( employeeService.updateStatus( statusForm ), CommonMessage.SUCCESS_UPDATE.getMessage() );
    }

    /**
     * 부서에 등록되지 않은 직원 목록 조회
     * @param condition : 검색조건 (사원명)
     * @param pageable : 검색조건 (page)
     * @return EmployeeStatus : 변경된 상태
     */
    @GetMapping(PREFIX_URL+"/departmentless/id/{departmentId}")
    public ApiUtil.ApiResult<Page<EmployeeDto>> getEmployeeListNotRegistered(
            @PathVariable Long departmentId,
            EmployeeSearchForm condition,
            Pageable pageable ) {
        log.error("departmentId ::: {}", departmentId);
        return success( employeeService.getEmployeeListNotRegistered( departmentId, condition, pageable ) );
    }

}
