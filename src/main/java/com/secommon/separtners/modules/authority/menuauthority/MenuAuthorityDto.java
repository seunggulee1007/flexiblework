package com.secommon.separtners.modules.authority.menuauthority;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class MenuAuthorityDto {

    private Long menuAuthorityId;

    /** 권한 그룹 식별자 */
    private Long authorityGroupId;

    /** 권한 그룹 명 */
    private String authorityGroupName;

    private Long menuId;

    private MenuRole menuRole;


    @QueryProjection
    public MenuAuthorityDto(Long menuAuthorityId, Long authorityGroupId, String authorityGroupName, Long menuId, MenuRole menuRole) {
        this.menuAuthorityId = menuAuthorityId;
        this.authorityGroupId = authorityGroupId;
        this.authorityGroupName = authorityGroupName;
        this.menuId = menuId;
        this.menuRole = menuRole;
    }

}
