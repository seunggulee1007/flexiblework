package com.secommon.separtners.modules.flexiblework.form;

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

    private LocalTime startTime;

    private LocalTime endTime;

}
