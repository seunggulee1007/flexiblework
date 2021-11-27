package com.secommon.separtners.modules.company.employeemanagement;

import com.secommon.separtners.infra.AbstractContainerBaseTest;
import com.secommon.separtners.infra.MockMvcTest;
import com.secommon.separtners.modules.company.department.Department;
import com.secommon.separtners.modules.company.department.repository.DepartmentRepository;
import com.secommon.separtners.modules.company.employee.Employee;
import com.secommon.separtners.modules.company.employee.repository.EmployeeRepository;
import com.secommon.separtners.modules.company.employeedepartment.EmployeeDepartment;
import com.secommon.separtners.modules.company.employeedepartment.EmployeeDepartmentRepository;
import com.secommon.separtners.modules.company.employeemanagement.form.EmployeeForm;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@MockMvcTest
class EmployeeManagementServiceTest extends AbstractContainerBaseTest {

    @Autowired
    private EmployeeManagementService employeeManagementService;
    @Autowired
    private EmployeeManagementRepository employeeManagementRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeDepartmentRepository employeeDepartmentRepository;

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
        List<Long> ids = employeeManagementService.registerEmployeeToDepartment( employeeFormList );

        // then
        for ( Long id: ids ) {
            EmployeeManagement employeeManagement = employeeManagementRepository.getById( id );
            assertEquals(employeeManagement.getEmployee(), employee);
            assertEquals(employeeManagement.getWillChangeDepartment(), department);
        }
        assertFalse( employee.getEmployeeDepartmentList().stream().filter( departments -> departments.getDepartment() == department ).findFirst().isEmpty() );

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
        employeeManagementService.registerEmployeeToDepartment( employeeFormList );

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
        employeeManagementService.registerEmployeeToDepartment( employeeFormList );

        // then
        EmployeeDepartment employeeDepartment = employeeDepartmentRepository.findById( 2L ).orElseThrow();
        assertEquals( employeeDepartment.getDepartment(), department1 );
        assertEquals( employeeDepartment.getEmployee(), employee );

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
        employeeManagementService.registerEmployeeToDepartment( employeeFormList );

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
        employeeManagementService.registerEmployeeToDepartment( employeeFormList );

        // then
        EmployeeDepartment employeeDepartment = employeeDepartmentRepository.findById( 3L ).orElseThrow();
        assertEquals( employeeDepartment.getDepartment(), department );
        assertEquals( employeeDepartment.getEmployee(), employee );

    }
    
}