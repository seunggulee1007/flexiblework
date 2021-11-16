package com.secommon.separtners.modules.flexiblework.form;

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

    private LocalTime startTime;

    private LocalTime endTime;

}
