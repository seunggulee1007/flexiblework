package com.secommon.separtners.modules.flexiblework.flexiblework.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.secommon.separtners.modules.flexiblework.flexiblework.MandatoryTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class MandatoryTimeForm {

    private Long mandatoryTimeId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime endTime;

    public MandatoryTimeForm( MandatoryTime mandatoryTime ) {
        this.mandatoryTimeId = mandatoryTime.getId();
        this.startTime = mandatoryTime.getStartTime();
        this.endTime = mandatoryTime.getEndTime();
    }

}
