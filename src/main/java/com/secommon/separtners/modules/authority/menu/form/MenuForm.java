package com.secommon.separtners.modules.authority.menu.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class MenuForm {

    /** 메뉴 명 */
    @NotEmpty(message = "메뉴 명이 누락되었습니다.")
    private String menuName;

    String menuPath;

    /** 페이지 여부 */
    @Builder.Default
    private boolean page = false;

    /** 사용 여부 */
    @Builder.Default
    private boolean active = true;

    /** 순서 */
    @NotNull(message = "순번이 누락되었습니다.")
    @Min(value = 1, message = "순번은 1부터 시작됩니다.")
    private int orderNumber;

    @NotNull(message = "부모 메뉴식별자가 누락되었습니다.")
    private Long parentId;

}
