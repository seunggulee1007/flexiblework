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
        this.mockMvc.perform( post("/api/department")
                .contentType( MediaType.APPLICATION_JSON )
                .content( this.objectMapper.writeValueAsString( form ) )
        )
                .andDo( print() )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "response" ).isNumber() )
                .andExpect( jsonPath( "response" ).value( 2L ) )
                .andExpect( jsonPath( "success" ).value( true ) )
                ;
        Optional<Department> optionalDepartment = departmentRepository.findById( 2L );
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

    @DisplayName("부서등록 실패 - 잘못된 입력값.")
    @WithMockJwtAuthentication
    @Test
    @Order( 2 )
    void  registerDepartment_fail_wrong_input () throws Exception {
        // given
        DepartmentForm form = DepartmentForm.builder()
                .departmentName( "영업부서" )
                .parentId( 1L )
                .build();
        // when
        this.mockMvc.perform( post("/api/department")
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( this.objectMapper.writeValueAsString( form ) )
                )
                .andDo( print() )
                .andExpect( status().isBadRequest() )
                .andExpect( jsonPath( "response" ).isEmpty() )
                .andExpect( jsonPath( "success" ).value( false ) )
                .andExpect( jsonPath( "message" ).value( "적용일자가 누락되었습니다." ) )
        ;
        // then
        Optional<Department> optionalDepartment = departmentRepository.findById( 3L );
        assertTrue( optionalDepartment.isEmpty() );
        assertThrows( BadRequestException.class, ()-> optionalDepartment.orElseThrow(()-> new BadRequestException("없는걸 왜 꺼낼라 그러냐")) );
    }

}