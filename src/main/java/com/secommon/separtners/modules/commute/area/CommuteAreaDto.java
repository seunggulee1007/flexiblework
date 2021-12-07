package com.secommon.separtners.modules.commute.area;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static org.springframework.beans.BeanUtils.copyProperties;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class CommuteAreaDto {

    private Long commuteAreaId;

    /** 출퇴근 지역 명 */
    private String areaName;

    /** 제한 범위 거리 */
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
    private String zoneCode;
    /** 위도 */
    private Double latitude;
    /** 경도 */
    private Double longitude;
    /** 활성 여부 */
    private boolean active;

    public CommuteAreaDto(CommuteArea commuteArea) {
        copyProperties(commuteArea, this);
        copyProperties(commuteArea.getAddress(), this);
        this.commuteAreaId = commuteArea.getId();
    }

}
