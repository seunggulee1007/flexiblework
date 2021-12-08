package com.secommon.separtners.modules.flexiblework.flexibleworkgroup.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class FlexibleWorkGroupForm {

    /** 그룹 아이디 */
    private Long flexibleWorkGroupId;

    /** 적용될 유연근무 아이디 */
    private Long flexibleWorkId;

    /** 그룹 명 */
    private String flexibleWorkGroupName;

    /** 적용할 사원 아이디 목록 */
    private List<Long> accountIdList;

}
