package com.secommon.separtners.modules.authority.authoritygroup.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class AuthorityGroupUpdateForm {

    @NotNull(message = "변경을 원하시는 그룹을 선택해 주세요.")
    private Long authorityGroupId;

    @NotEmpty(message = "그룹명을 입력해 주세요.")
    private String authorityGroupName;

    private boolean active;

    @Builder.Default
    private boolean basic = false;

    @Builder.Default
    private boolean admin  = false;

    /** 유저 아이디 리스트 */
    @Builder.Default
    private List<Long> accountIds = new ArrayList<>();

}
