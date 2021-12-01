package com.secommon.separtners.modules.company.department;

import com.secommon.separtners.modules.company.employee.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class DepartmentDto {

    private Long departmentId;

    private String departmentName;

    private String departmentCode;

    private Long parentId;

    private boolean active;

    private List<DepartmentDto> children;

    private List<EmployeeDto> employeeDtoList;

    public DepartmentDto( Department department) {
        this.departmentId = department.getId();
        this.departmentName = department.getDepartmentName();
        if(department.getParent() != null) {
            this.parentId = department.getParent().getId();
        }
        this.children = department.getChild().stream().map( DepartmentDto::new ).collect( Collectors.toList());
        this.employeeDtoList = department.getEmployeeDepartmentList().stream().map( employeeDepartment -> new EmployeeDto(employeeDepartment.getEmployee()) ).collect( Collectors.toList());
    }

}
