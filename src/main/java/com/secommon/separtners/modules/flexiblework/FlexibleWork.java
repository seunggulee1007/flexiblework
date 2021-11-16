package com.secommon.separtners.modules.flexiblework;

import com.secommon.separtners.modules.flexiblework.enums.DailyWorkTime;
import com.secommon.separtners.modules.flexiblework.enums.FlexibleWorkType;
import com.secommon.separtners.modules.flexiblework.enums.SettlementUnitPeriod;
import com.secommon.separtners.modules.flexiblework.enums.WorkDayOfWeek;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Entity
@Builder @Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FlexibleWork {

    /** 유연근무 일련번호 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flexible_work_id")
    private Long id;

    /** 유연근무제도 명( 기본 주 52시간 ) */
    @Builder.Default
    private FlexibleWorkType flexibleWorkType = FlexibleWorkType.WEEK_52;

    /** 유연근무 명칭 */
    private String flexibleWorkName;

    /** 근무 요일 */
    @ElementCollection(fetch = LAZY)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "work_day_of_week", joinColumns = @JoinColumn(name = "flexible_work_id"))
    private Set<WorkDayOfWeek> workDayOfWeeks;

    /** 1일 근로시간 */
    private DailyWorkTime dailyWorkTime;

    /** 정산 단위기간 */
    private SettlementUnitPeriod settlementUnitPeriod;

    /** 적용기간 from */
    private LocalDate applyDateFrom;

    /** 적용기간 to */
    private LocalDate applyDateTo;

    private LocalTime startTime;

    private LocalTime endTime;

    /** 휴게시간 유무 */
    private boolean restExist;

    /** 의무 시간 유무 */
    private boolean mandatoryTimeExist;

    @OneToMany(mappedBy = "flexibleWork", fetch = LAZY)
    private List<RestTime> restTimeList;

    @OneToMany(mappedBy = "flexibleWork")
    private List<MandatoryTime> mandatoryTimeList;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "flexible_work_group_id")
    private FlexibleWorkGroup flexibleWorkGroup;

    public void setApplyDate () {
        if(this.applyDateTo == null) {
            this.applyDateTo = LocalDate.of(9999, Month.DECEMBER, 31);
        }
    }

}