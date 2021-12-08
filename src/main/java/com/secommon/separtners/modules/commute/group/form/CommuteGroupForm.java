package com.secommon.separtners.modules.commute.group.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class CommuteGroupForm {

    @NotNull(message = "출근 허용지역을 선택해 주세요.")
    private Long commuteAreaId;

    @NotEmpty(message = "그룹명을 입력해 주세요.")
    private String groupName;

    @Builder.Default
    private boolean active = true;

    @NotEmpty(message = "사원 아이디가 누락되었습니다.")
    @Min(value = 1, message = "적용을 원하시는 사원을 한명 이상 선택해 주세요.")
    @Builder.Default
    private List<Long> accountIdList = new ArrayList<>();

}
