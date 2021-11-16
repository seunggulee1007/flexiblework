package com.secommon.separtners.modules.commute;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class CommuteDto {

    private Long commuteId;

    private LocalDateTime onWorkDate;

    private LocalDateTime offWorkDate;

    private LocalDateTime workedDate;

    private Long accountId;

    public CommuteDto(Commute commute) {
        copyProperties(commute, this);
        this.commuteId = commute.getId();
        this.accountId = commute.getAccount().getId();
        this.workedDate = commute.getCreatedDate() == null ? LocalDateTime.now() : commute.getCreatedDate();
    }

}
