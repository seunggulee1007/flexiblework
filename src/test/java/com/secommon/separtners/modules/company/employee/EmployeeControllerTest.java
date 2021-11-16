package com.secommon.separtners.modules.company.employee;

import com.secommon.separtners.infra.AbstractContainerBaseTest;
import com.secommon.separtners.infra.MockMvcTest;
import com.secommon.separtners.infra.security.WithMockJwtAuthentication;
import com.secommon.separtners.modules.company.employee.form.PositionForm;
import com.secommon.separtners.modules.company.employee.form.StatusForm;
import com.secommon.separtners.modules.company.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
class EmployeeControllerTest extends AbstractContainerBaseTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("정상적으로 직급이 변경")
    @WithMockJwtAuthentication
    @Order( 1 )
    void  changePositionO () throws Exception {
        Employee employee = Employee.builder()
                .build();
        employeeRepository.save( employee );
        Position position = employee.getPosition();
        // given
        PositionForm positionForm = PositionForm
                .builder()
                .employeeId( employee.getId() )
                .position( Position.CHAIRMAN )
                .build();
        // when
        this.mockMvc.perform( put("/api/employee/position")
                .contentType( MediaType.APPLICATION_JSON )
                .content( this.objectMapper.writeValueAsString( positionForm ) )
        )
                .andDo( print() )
                .andExpect( status().isOk() )
                ;
        Employee employee1 = employeeRepository.findById( employee.getId() ).orElseGet( Employee::new );
        // then
        assertEquals( Position.EMPLOYEE, position );
        assertEquals( Position.CHAIRMAN, employee1.getPosition());
        
    }


    @Test
    @DisplayName("직급 변경 실패 - 입력값 오류")
    @WithMockJwtAuthentication
    @Order( 2 )
    void updatePositionX () throws Exception {
        // given
        PositionForm positionForm = PositionForm
                .builder()
                .employeeId( 1L )
                .build();
        // when
        this.mockMvc.perform( put("/api/employee/position")
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( this.objectMapper.writeValueAsString( positionForm ) )
                )
                .andDo( print() )
                .andExpect( status().isBadRequest() )
        ;

        // then
        Employee employee = employeeRepository.findById( 1L ).orElseGet( Employee::new );
        assertEquals( Position.EMPLOYEE, employee.getPosition() );

    }

    @Test
    @DisplayName("직급 변경 실패 - 인증 오류")
    @Order( 3 )
    void updatePositionX_NoAuthentication () throws Exception {
        // given
        PositionForm positionForm = PositionForm
                .builder()
                .employeeId( 1L )
                .position( Position.CHAIRMAN )
                .build();
        // when
        this.mockMvc.perform( put("/api/employee/position")
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( this.objectMapper.writeValueAsString( positionForm ) )
                )
                .andDo( print() )
                .andExpect( status().isUnauthorized() )
        ;

        // then
        Employee employee = employeeRepository.findById( 1L ).orElseGet( Employee::new );
        assertEquals( Position.EMPLOYEE, employee.getPosition() );

    }


    @Test
    @DisplayName("사원 상태 변경")
    @WithMockJwtAuthentication
    void updateStatusO () throws Exception {
        // given
        Employee employee = Employee.builder()
                .build();
        employeeRepository.save( employee );
        StatusForm statusForm = StatusForm.builder()
                .employeeId( employee.getId() )
                .employeeStatus( EmployeeStatus.HIRE )
                .build();
        // when
        this.mockMvc.perform( put("/api/employee/status")
                .content( this.objectMapper.writeValueAsString( statusForm ) )
                .contentType( MediaType.APPLICATION_JSON )
        )
                .andDo( print() )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "response" ).value( EmployeeStatus.HIRE.name() ) )

                ;

        // then
        employee = employeeRepository.findById( employee.getId() ).orElseThrow();
        assertEquals( EmployeeStatus.HIRE, employee.getStatus() );

    }

}