package com.secommon.separtners.modules.flexiblework.flexibleworkplan;

import com.secommon.separtners.modules.account.Account;
import com.secommon.separtners.modules.common.UpdatedEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FlexibleWorkPlan extends UpdatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flexible_work_plan_id")
    private Long id;

    /** 계획 년도 */
    private String planYear;

    /** 계획 월 */
    private String planMonth;

    /** 계획 일 */
    private String planDay;

    /** 계획일자 */
    private LocalDate planDate;

    /** 시작 시간 */
    private LocalTime startTime;

    /** 종료 시간 */
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public void calcDate() {
        if(this.planDate != null) {
            this.planYear = this.planDate.format(DateTimeFormatter.ofPattern("yyyy"));
            this.planMonth = this.planDate.format(DateTimeFormatter.ofPattern("MM"));
            this.planDay = this.planDate.format(DateTimeFormatter.ofPattern("dd"));
        }
    }
}