package com.secommon.separtners.modules.commute.area.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class CommuteAreaUpdateForm {

    /** 출근 허용 지역 식별번호 */
    @NotNull(message = "변경을 원하시는 출근 허용 지역을 선택해 주세요.")
    private Long commuteAreaId;

    /** 출퇴근 지역 명 */
    @NotEmpty(message = "출퇴근 지역명을 입력해 주세요.")
    private String areaName;

    /** 제한 범위 거리 */
    @NotEmpty(message = "제한 범위 거리를 입력해 주세요.")
    private int distance;

    /** 도시 (시/도) */
    private String city;
    /** 도로명 주소 */
    private String roadName;
    /** 상세주소 */
    private String detailAddress;
    /** 시군구 */
    private String siGunGu;
    /** 동 */
    private String dong;
    /** 빌딩 명 */
    private String buildingName;
    /** 빌딩 번호 */
    private String buildingNumber;
    /** 우편번호 */
    @NotEmpty(message = "출퇴근 장소를 선택해 주세요.")
    private String zoneCode;
    /** 위도 */
    @NotEmpty(message = "위도 경도가 입력되지 않았습니다.")
    private Double latitude;
    /** 경도 */
    @NotEmpty(message = "위도 경도가 입력되지 않았습니다.")
    private Double longitude;

    /** 활성 여부 */
    private boolean active;

}
