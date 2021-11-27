package com.secommon.separtners.modules.flexiblework.flexibleworkgroup;

import com.secommon.separtners.modules.company.employee.Employee;
import com.secommon.separtners.modules.flexiblework.flexiblework.FlexibleWork;
import com.secommon.separtners.modules.flexiblework.flexibleworkgroup.form.FlexibleWorkGroupForm;
import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
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

    /** 근무 그룹 명칭 */
    private String flexibleWorkGroupName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "flexible_work_id")
    private FlexibleWork flexibleWork;

    @OneToMany(mappedBy = "flexibleWorkGroup", fetch = LAZY)
    @Builder.Default
    private List<Employee> employeeList = new ArrayList<>();

    private boolean active;

    public void addEmployee ( Employee employee ) {
        employee.setWorkGroup(this);
    }

    public int countEmployee() {
        return this.employeeList.size();
    }

    public void updateFlexibleWorkGroup ( FlexibleWork flexibleWork, FlexibleWorkGroupForm flexibleWorkGroupForm ) {
        if(flexibleWork != this.flexibleWork) {
            this.flexibleWork.getFlexibleWorkGroupList().remove( this );
            this.flexibleWork = flexibleWork;
            this.flexibleWork.getFlexibleWorkGroupList().add( this );
        }
        if( StringUtils.hasText(flexibleWorkGroupForm.getFlexibleWorkGroupName())) {
            this.flexibleWorkGroupName = flexibleWorkGroupForm.getFlexibleWorkGroupName();
        }

    }
}
