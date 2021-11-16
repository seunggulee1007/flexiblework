package com.secommon.separtners.modules.company.department;

import com.secommon.separtners.infra.advice.exceptions.BadRequestException;
import com.secommon.separtners.infra.commons.BaseServiceAnnotation;
import com.secommon.separtners.modules.company.departmenmanagement.DepartmentManagement;
import com.secommon.separtners.modules.company.departmenmanagement.repository.DepartmentManagementRepository;
import com.secommon.separtners.modules.company.department.form.DepartmentForm;
import com.secommon.separtners.modules.company.department.form.DepartmentSearchCondition;
import com.secommon.separtners.modules.company.department.form.DepartmentUpdateForm;
import com.secommon.separtners.modules.company.department.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@BaseServiceAnnotation
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentManagementRepository departmentManagementRepository;

    @Transactional(readOnly = true)
    public List<DepartmentDto> findAllDepartmentList( DepartmentSearchCondition condition ) {
        List<Department> departmentList = departmentRepository.findAllDepartment( condition );
        List<Department> collect = departmentList.stream().filter( department -> department.getParent() == null ).collect( Collectors.toList() );
        DepartmentDto departmentDto = new DepartmentDto(collect.get(0));
        return Collections.singletonList( departmentDto );
    }

    /**
     * 부서 등록
     * @param departmentForm : 부서 등록 정보
     * @return Long : 등록된 부서 아이디
     */
    public Long createNewDepartment( DepartmentForm departmentForm ) {
        Department department = saveDepartment( departmentForm );
        if( departmentForm.getParentId() != null) {
            Department parentDepartment = departmentRepository.findById( departmentForm.getParentId() ).orElseThrow();
            parentDepartment.addChildDepartment( department );
        }
        saveManagement(
            department,
            departmentForm.getDepartmentName(),
            departmentForm.getDepartmentCode(),
            departmentForm.getApplyDate(),
            department.isActive()
        );
        return department.getId();
    }

    /**
     * 부서 저장
     * @param departmentForm : 부서 등록 폼
     */
    private Department saveDepartment ( DepartmentForm departmentForm ) {
        Department department = Department.builder()
                .departmentName( departmentForm.getDepartmentName() )
                .departmentCode( departmentForm.getDepartmentCode() )
                .active( departmentForm.isRightNow() )
                .build();
        departmentRepository.save( department );
        return department;
    }

    /**
     * 부서 수정
     * @param departmentUpdateForm : 수정 폼
     */
    public Long updateDepartment( DepartmentUpdateForm departmentUpdateForm ) {
        Department department = departmentRepository.findById( departmentUpdateForm.getDepartmentId() ).orElseThrow(()-> new BadRequestException("해당 아이디로 등록된 부서가 없습니다.") );
        DepartmentManagement departmentManagement = saveManagement(
                department,
                departmentUpdateForm.getDepartmentName(),
                departmentUpdateForm.getDepartmentCode(),
                departmentUpdateForm.getApplyDate(),
                departmentUpdateForm.isActive()
        );
        if (departmentUpdateForm.isRightNow()) {
            department.updateDepartment(departmentManagement);
        }
        return department.getId();
    }

    /**
     * 부서 관리 등록
     * @param department : 부서
     * @param departmentName : 부서명
     * @param departmentCode : 부서 코드
     * @param applyDate : 적용일자
     * @param active : 사용여부
     */
    private DepartmentManagement saveManagement ( Department department, String departmentName, String departmentCode, LocalDate applyDate, boolean active ) {
        DepartmentManagement departmentManagement = DepartmentManagement.builder()
                .department( department )
                .departmentName( departmentName )
                .departmentCode( departmentCode )
                .applyDate( applyDate )
                .active( active )
                .build();
        departmentManagementRepository.save( departmentManagement );
        return departmentManagement;
    }

}
