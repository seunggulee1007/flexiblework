package com.secommon.separtners.modules.flexiblework.flexiblework.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.secommon.separtners.modules.flexiblework.flexiblework.RestTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class RestTimeForm {

    private Long restTimeId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime endTime;

    public RestTimeForm( RestTime restTime ) {
        this.restTimeId = restTime.getId();
        this.startTime = restTime.getStartTime();
        this.endTime = restTime.getEndTime();
    }

}
