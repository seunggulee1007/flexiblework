package com.secommon.separtners.modules.flexiblework.flexiblework;

import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Builder @Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MandatoryTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mandatory_time_id")
    private Long id;

    private LocalTime startTime;

    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "flexible_work_flexible_work_id")
    private FlexibleWork flexibleWork;

    public FlexibleWork getFlexibleWork () {
        return flexibleWork;
    }

    public void setFlexibleWork ( FlexibleWork flexibleWork ) {
        this.flexibleWork = flexibleWork;
        this.flexibleWork.getMandatoryTimeList().add( this );
    }

    public void deleteFlexibleWork() {
        int idx = this.flexibleWork.getMandatoryTimeList().indexOf( this );
        if(idx != -1) {
            this.flexibleWork.getMandatoryTimeList().remove( idx );
        }
        this.flexibleWork = null;
    }

}