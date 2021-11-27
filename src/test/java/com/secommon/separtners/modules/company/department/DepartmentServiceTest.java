package com.secommon.separtners.modules.company.department;

import com.secommon.separtners.infra.AbstractContainerBaseTest;
import com.secommon.separtners.infra.MockMvcTest;
import com.secommon.separtners.infra.advice.exceptions.BadRequestException;
import com.secommon.separtners.infra.security.WithMockJwtAuthentication;
import com.secommon.separtners.modules.company.department.form.DepartmentForm;
import com.secommon.separtners.modules.company.department.repository.DepartmentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
class DepartmentServiceTest extends AbstractContainerBaseTest {

    @Autowired
    DepartmentService departmentService;
    @Autowired
    DepartmentRepository departmentRepository;

    @DisplayName("부서등록이 정상적일때")
    @Test
    @WithMockJwtAuthentication
    @Order( 1 )
    void registerDepartment_success() throws Exception {
        saveDepartment();
        // given
        DepartmentForm form = DepartmentForm.builder()
                .departmentName( "영업부서" )
                .parentId( 1L )
                .rightNow( true )
            .build();
        // when
        Long newDepartment = departmentService.createNewDepartment( form );

        Optional<Department> optionalDepartment = departmentRepository.findById( newDepartment );
        assertTrue( optionalDepartment.isPresent() );
        assertNotNull( optionalDepartment.orElse( null ) );
        Department department = optionalDepartment.get();
        // then
        assertEquals( 1L, department.getParent().getId() );
        assertEquals( 2L, department.getId() );
    }

    private void saveDepartment () {
        Department department = Department.builder()
                .departmentName( "Separtners" )
                .build();
        departmentRepository.save( department );
    }

}