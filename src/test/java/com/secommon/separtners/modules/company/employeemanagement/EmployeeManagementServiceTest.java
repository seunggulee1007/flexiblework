package com.secommon.separtners.modules.company.employeemanagement;

import com.secommon.separtners.infra.AbstractContainerBaseTest;
import com.secommon.separtners.infra.MockMvcTest;
import com.secommon.separtners.modules.company.department.Department;
import com.secommon.separtners.modules.company.department.repository.DepartmentRepository;
import com.secommon.separtners.modules.company.employee.Employee;
import com.secommon.separtners.modules.company.employee.repository.EmployeeRepository;
import com.secommon.separtners.modules.company.employeemanagement.form.EmployeeForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@MockMvcTest
class EmployeeManagementServiceTest extends AbstractContainerBaseTest {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("부서에 사원 등록 테스트")
    @Order( 1 )
    void registerEmployeeO  () throws Exception {
        // given
        Department department = Department.builder()
                .departmentName( "Department" )
                .active( true )
            .build();
        departmentRepository.save( department );
        Long departmentId = department.getId();
        Employee employee = Employee.builder()
                .build();
        employeeRepository.save( employee );
        Long employeeId = employee.getId();
        // when
        EmployeeForm employeeForm = EmployeeForm.builder()
                .employeeId( employeeId )
                .departmentId( departmentId )
                .rightNow( true )
                .build();
        List<EmployeeForm> employeeFormList = new ArrayList<>();
        employeeFormList.add( employeeForm );

        // then

    }


    @Test
    @DisplayName("사원의 부서 변경 - 즉시 변경")
    @Order( 2 )
    void  changeDepartmentOfEmployee () throws Exception {
        // given
        Department department = Department.builder()
                .departmentName( "Department" )
                .active( true )
                .build();
        departmentRepository.save( department );
        Long departmentId = department.getId();
        Employee employee = Employee.builder()
                .build();
        employeeRepository.save( employee );
        Long employeeId = employee.getId();
        EmployeeForm employeeForm = EmployeeForm.builder()
                .employeeId( employeeId )
                .departmentId( departmentId )
                .rightNow( true )
                .build();
        List<EmployeeForm> employeeFormList = new ArrayList<>();
        employeeFormList.add( employeeForm );

        // when
        Department department1 =  Department.builder()
                .departmentName( "Department" )
                .active( true )
                .build();
        departmentRepository.save( department1 );
        Long departmentId1 = department1.getId();
        employeeForm = EmployeeForm.builder()
                .employeeDepartmentId( 2L )
                .employeeId( employeeId )
                .departmentId( departmentId1 )
                .rightNow( true )
                .build();
        employeeFormList = new ArrayList<>();
        employeeFormList.add( employeeForm );

    }

    @Test
    @DisplayName("사원의 부서 변경 - 적용일자에 따른 변경")
    @Order( 3 )
    void  changeDepartmentOfEmployeeWithApplyDate () throws Exception {
        // given
        Department department = Department.builder()
                .departmentName( "Department" )
                .active( true )
                .build();
        departmentRepository.save( department );
        Long departmentId = department.getId();
        Employee employee = Employee.builder()
                .build();
        employeeRepository.save( employee );
        Long employeeId = employee.getId();
        EmployeeForm employeeForm = EmployeeForm.builder()
                .employeeId( employeeId )
                .departmentId( departmentId )
                .rightNow( true )
                .build();
        List<EmployeeForm> employeeFormList = new ArrayList<>();
        employeeFormList.add( employeeForm );
        // when
        Department department1 =  Department.builder()
                .departmentName( "Department" )
                .active( true )
                .build();
        departmentRepository.save( department1 );
        Long departmentId1 = department1.getId();
        employeeForm = EmployeeForm.builder()
                .employeeDepartmentId( 3L )
                .employeeId( employeeId )
                .departmentId( departmentId1 )
                .rightNow( false )
                .build();
        employeeFormList = new ArrayList<>();
        employeeFormList.add( employeeForm );

    }
    
}