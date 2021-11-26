package com.secommon.separtners.infra.config;

import com.secommon.separtners.infra.properties.AppProperties;
import com.secommon.separtners.modules.account.Account;
import com.secommon.separtners.modules.account.enums.AccountRole;
import com.secommon.separtners.modules.account.repository.AccountRepository;
import com.secommon.separtners.modules.authority.menu.Menu;
import com.secommon.separtners.modules.authority.menu.MenuRepository;
import com.secommon.separtners.modules.authority.menu.MenuService;
import com.secommon.separtners.modules.company.department.Department;
import com.secommon.separtners.modules.company.department.repository.DepartmentRepository;
import com.secommon.separtners.modules.company.employee.Employee;
import com.secommon.separtners.modules.company.employee.repository.EmployeeRepository;
import com.secommon.separtners.modules.company.employeedepartment.EmployeeDepartment;
import com.secommon.separtners.modules.company.employeedepartment.EmployeeDepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@Profile( {"local"} )
public class AppConfig {

    private final AppProperties appProperties;
    private final PasswordEncoder passwordEncoder;

    @Bean
    @Transactional
    public ApplicationRunner applicationRunner () {
        return new ApplicationRunner() {
            @Autowired
            AccountRepository accountRepository;
            @Autowired
            DepartmentRepository departmentRepository;
            @Autowired
            EmployeeRepository employeeRepository;
            @Autowired
            EmployeeDepartmentRepository employeeDepartmentRepository;
            @Autowired
            MenuRepository menuRepository;

            @Override
            @Transactional
            public void run ( ApplicationArguments args ) throws Exception {

                List<Department> departments = departmentRepository.findAll();
                Department department;
                if(departments.isEmpty()) {
                    department = Department.builder()
                            .departmentName( appProperties.getCompanyName() )
                            .build();
                    departmentRepository.save( department );
                    List<Account> accounts = accountRepository.findAll();
                    if (accounts.isEmpty()) {
                        Account account = Account.builder()
                                .email( "leesg107@naver.com" )
                                .userName( "이승구" )
                                .password( passwordEncoder.encode( "1q2w3e$R" ) )
                                .emailVerified( true )
                                .joinedAt( LocalDateTime.now() )
                                .roles( Set.of( AccountRole.ADMIN, AccountRole.USER) )
                                .build();
                        accountRepository.save( account );
                        Employee employee = saveEmployee(account);
                        saveEmployeeDepartment( department, employee );
                    } else {
                        Account account = accounts.get( 0 );
                        Employee employee = saveEmployee(account);
                        saveEmployeeDepartment( department, employee );
                    }
                } // end if
                List<Menu> menuList = menuRepository.findAll();
                if(menuList.isEmpty()) {
                    Menu menu = Menu.builder()
                            .menuName( appProperties.getCompanyName() )
                            .order( 1 )
                            .build();
                    menuRepository.save( menu );
                }
            } // end run

            private void saveEmployeeDepartment ( Department department, Employee employee ) {
                EmployeeDepartment employeeDepartment = EmployeeDepartment.builder()
                        .department( department )
                        .employee( employee )
                        .build();
                employeeDepartmentRepository.save( employeeDepartment );
            }

            public Employee saveEmployee(Account account) {
                Employee employee = Employee.builder()
                        .account( account )
                        .hireDate( LocalDateTime.now() )
                        .build();
                employeeRepository.save( employee );
                return employee;
            }
        };
    }
}
