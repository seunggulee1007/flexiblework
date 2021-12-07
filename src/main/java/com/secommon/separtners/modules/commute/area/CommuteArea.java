package com.secommon.separtners.modules.commute.area;

import com.secommon.separtners.modules.common.Address;
import com.secommon.separtners.modules.common.UpdatedEntity;
import com.secommon.separtners.modules.commute.area.form.CommuteAreaUpdateForm;
import com.secommon.separtners.modules.commute.group.CommuteGroup;
import com.secommon.separtners.modules.company.employee.Employee;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

/**
 * 출퇴근 지역 관리 테이블
 */
@Entity
@Builder @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class CommuteArea extends UpdatedEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="commute_area_id")
    private Long id;

    /** 출퇴근 지역 명 */
    private String areaName;

    @Embedded
    private Address address;

    /** 제한 범위 */
    private int distance;

    /** 활성 여부 */
    private boolean active;

    @OneToMany(mappedBy = "commuteArea", fetch = LAZY)
    @Builder.Default
    private List<CommuteGroup> commuteGroupList = new ArrayList<>();

    @OneToMany(mappedBy = "commuteArea",fetch = LAZY)
    @Builder.Default
    private List<Employee> employeeList = new ArrayList<>();

    public void changeAddress(Address address) {
        this.address = address;
    }

    public void changeItems(CommuteAreaUpdateForm commuteAreaUpdateForm) {
        this.areaName = commuteAreaUpdateForm.getAreaName();
        this.distance = commuteAreaUpdateForm.getDistance();
        this.active = commuteAreaUpdateForm.isActive();
    }

}
