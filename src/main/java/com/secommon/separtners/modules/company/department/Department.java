package com.secommon.separtners.modules.company.department;

import com.secommon.separtners.modules.common.UpdatedEntity;
import com.secommon.separtners.modules.company.departmenmanagement.DepartmentManagement;
import com.secommon.separtners.modules.company.mapping.EmployeeDepartment;
import com.secommon.separtners.modules.flexiblework.FlexibleWorkGroup;
import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class Department extends UpdatedEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long id;

    /** 부서 이름 */
    private String departmentName;

    /** erp 연동용 부서 코드 */
    private String departmentCode;

    /** 사용 여부 */
    @Builder.Default
    private boolean active = false;

    /** 부서 전체 경로 */
    private String departmentPath;

    /** 상위 부서 */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Department parent;

    /** 하위 부서 */
    @OneToMany(mappedBy = "parent")
    @Builder.Default
    private List<Department> child = new ArrayList<>();

    /** 사원 리스트 */
    @OneToMany(mappedBy = "department")
    @Builder.Default
    private List<EmployeeDepartment> employeeDepartmentList = new ArrayList<>();

    /** 해당 부서 관리 리스트 */
    @OneToMany(fetch = LAZY, mappedBy = "department")
    private List<DepartmentManagement> departmentManagementList;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "flexible_work_group_id")
    private FlexibleWorkGroup flexibleWorkGroup;

    public void setSettingParent(Department department) {
        if(this.parent != null) {
            clearParent();
        }
        this.parent = department;
        this.parent.child.add( this );
        this.setPath();
    }

    public void clearParent() {
        final int index = this.parent.child.indexOf( this );
        if(index != -1) {
            this.parent.child.remove( index );
        }
        this.parent = null;
    }

    public void addChildDepartment(Department child) {
        this.child.add( child );
        child.setSettingParent( this );
    }

    public void setPath() {
        if( StringUtils.hasText(this.parent.departmentPath)) {
            this.departmentPath = this.parent.departmentPath + " > " + this.id;
        } else {
            this.departmentPath = "" + this.id;
        }
    }

    public void updateDepartment ( DepartmentManagement departmentManagement ) {
        if( StringUtils.hasText( departmentManagement.getDepartmentName() ) ) {
            this.departmentName = departmentManagement.getDepartmentName();
        }
        if( StringUtils.hasText( departmentManagement.getDepartmentCode() ) ) {
            this.departmentCode = departmentManagement.getDepartmentCode();
        }
        if( this.active != departmentManagement.isActive() ) {
            this.active = departmentManagement.isActive();
        }
    }

    public void setWorkGroup ( FlexibleWorkGroup flexibleWorkGroup ) {
        this.flexibleWorkGroup = flexibleWorkGroup;
        this.flexibleWorkGroup.getDepartmentList().add( this );
    }

}
