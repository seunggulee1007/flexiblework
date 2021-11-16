package com.secommon.separtners.modules.flexiblework;

import com.secommon.separtners.modules.company.department.Department;
import com.secommon.separtners.modules.company.employee.Employee;
import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Builder
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FlexibleWorkGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flexible_work_group_id")
    private Long id;

    @OneToMany(mappedBy = "flexibleWorkGroup", fetch = LAZY)
    private List<FlexibleWork> flexibleWorkList;


    @OneToMany(mappedBy = "flexibleWorkGroup", fetch = LAZY)
    private List<Department> departmentList;

    @OneToMany(mappedBy = "flexibleWorkGroup", fetch = LAZY)
    private List<Employee> employeeList;

    public void addDepartment ( Department department ) {
        department.setWorkGroup(this);
    }

    public void addEmployee ( Employee employee ) {
        employee.setWorkGroup(this);
    }
}
