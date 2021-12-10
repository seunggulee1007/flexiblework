package com.secommon.separtners.modules.flexiblework.flexibleworkgroup;

import com.secommon.separtners.modules.account.Account;
import com.secommon.separtners.modules.common.UpdatedEntity;
import com.secommon.separtners.modules.company.department.Department;
import com.secommon.separtners.modules.flexiblework.flexiblework.FlexibleWork;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Month;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FlexibleWorkGroup extends UpdatedEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flexible_work_group_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "flexible_id")
    private FlexibleWork flexibleWork;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = LAZY)
    private Department department;

    private LocalDate applyDateFrom;

    private LocalDate applyDateTo;

    public void saveWorkGroup() {
        if(this.applyDateTo == null) {
            this.applyDateTo = LocalDate.of(9999, Month.DECEMBER, 31);
        }
    }

}
