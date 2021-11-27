package com.secommon.separtners.modules.flexiblework.flexibleworkgroup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class FlexibleWorkGroupEmployee {

    private Long employeeId;

    private boolean deleteNode;

    private boolean newNode;

}
