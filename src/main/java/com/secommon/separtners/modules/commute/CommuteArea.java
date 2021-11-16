package com.secommon.separtners.modules.commute;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 출퇴근 지역 관리 테이블
 */
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class CommuteArea {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="commute_area_id")
    private Long id;

    /** 출퇴근 지역 명 */
    private String areaName;

    /** 위도 */
    private long latitude;

    /** 경도 */
    private long longitude;

    /** 제한 범위 */
    private int distance;

}
