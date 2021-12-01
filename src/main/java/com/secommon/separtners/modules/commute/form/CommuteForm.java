package com.secommon.separtners.modules.commute.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class CommuteForm {

    private Long commuteId;

    private LocalDateTime onWorkDate;

    private LocalDateTime offWorkDate;

    private boolean onOffFlag;

    private Long accountId;

}
