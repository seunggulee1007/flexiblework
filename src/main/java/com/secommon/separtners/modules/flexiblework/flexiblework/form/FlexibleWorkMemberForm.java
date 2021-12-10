package com.secommon.separtners.modules.flexiblework.flexiblework.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class FlexibleWorkMemberForm {

    @NotNull(message = "적용을 원하시는 유연근무를 선택해 주세요.")
    private Long flexibleWorkId;

    private List<Long> accountIdList;

    private List<Long> departmentIdList;

}
