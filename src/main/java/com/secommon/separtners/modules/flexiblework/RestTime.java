package com.secommon.separtners.modules.flexiblework;

import com.secommon.separtners.modules.flexiblework.form.RestTimeForm;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Builder @Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rest_time_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flexible_work_id")
    private FlexibleWork flexibleWork;

    private LocalTime startTime;

    private LocalTime endTime;

    public FlexibleWork getFlexibleWork () {
        return flexibleWork;
    }

    public void setFlexibleWork ( FlexibleWork flexibleWork ) {
        this.flexibleWork = flexibleWork;
        this.flexibleWork.getRestTimeList().add( this );
    }

    public void deleteFlexibleWork() {
        int idx = this.flexibleWork.getRestTimeList().indexOf( this );
        if (idx != -1) {
            this.flexibleWork.getRestTimeList().remove( idx );
        }
        this.flexibleWork = null;
    }

    public void changeTimes ( RestTimeForm restTimeForm ) {
        this.startTime = restTimeForm.getStartTime();
        this.endTime = restTimeForm.getEndTime();
    }

}