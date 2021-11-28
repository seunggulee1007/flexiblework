package com.secommon.separtners.modules.authority.menuauthority.form;

import com.secommon.separtners.modules.authority.menuauthority.MenuRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class MenuAuthorityUpdateForm {

    @NotNull(message = "변경을 원하시는 메뉴 권한 그룹을 선택해 주세요.")
    private Long menuAuthorityId;

    @NotNull(message = "변경을 원하시는 메뉴를 선택해 주세요.")
    private Long menuId;

    @NotNull(message = "변경을 원하시는 권한 그룹을 선택해 주세요.")
    private Long authorityGroupId;

    @NotEmpty
    private MenuRole menuRole;

}
