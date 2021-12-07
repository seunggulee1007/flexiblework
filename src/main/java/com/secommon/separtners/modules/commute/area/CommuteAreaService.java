package com.secommon.separtners.modules.commute.area;

import com.secommon.separtners.infra.commons.BaseServiceAnnotation;
import com.secommon.separtners.modules.common.Address;
import com.secommon.separtners.modules.commute.area.form.CommuteAreaForm;
import com.secommon.separtners.modules.commute.area.form.CommuteAreaSearchForm;
import com.secommon.separtners.modules.commute.area.form.CommuteAreaUpdateForm;
import com.secommon.separtners.modules.commute.area.repository.CommuteAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@BaseServiceAnnotation
@RequiredArgsConstructor
public class CommuteAreaService {

    private final CommuteAreaRepository commuteAreaRepository;


    public Page<CommuteAreaDto> findAllByCommuteAreaSearchForm(CommuteAreaSearchForm searchForm, Pageable pageable) {
        return commuteAreaRepository.findAllByCommuteAreaSearchForm(searchForm, pageable);
    }

    public CommuteAreaDto findOneById(Long commuteAreaId) {
        CommuteArea commuteArea = commuteAreaRepository.findById(commuteAreaId).orElseThrow();
        return new CommuteAreaDto(commuteArea);
    }

    /**
     * 신규 저장
     * @param commuteAreaForm : 저장 정보
     * @return 저장된 아이디
     */
    public Long saveCommuteArea(CommuteAreaForm commuteAreaForm) {
        Address address = buildAddress(commuteAreaForm);
        CommuteArea commuteArea = saveNewCommuteArea(commuteAreaForm, address);
        return commuteArea.getId();
    }

    /**
     * 출퇴근 허용 지역 수정
     * @param commuteAreaUpdateForm : 수정된 정보
     * @return 수정된 아이디
     */
    public Long modifyCommuteArea(CommuteAreaUpdateForm commuteAreaUpdateForm) {
        CommuteArea commuteArea = commuteAreaRepository.findById(commuteAreaUpdateForm.getCommuteAreaId()).orElseThrow();
        commuteArea.changeItems(commuteAreaUpdateForm);
        CommuteAreaForm commuteAreaForm = new CommuteAreaForm((commuteAreaUpdateForm));
        Address address = buildAddress(commuteAreaForm);
        commuteArea.changeAddress(address);
        return commuteArea.getId();
    }

    private CommuteArea saveNewCommuteArea(CommuteAreaForm commuteAreaForm, Address address) {
        CommuteArea commuteArea = CommuteArea.builder()
                .areaName(commuteAreaForm.getAreaName())
                .distance(commuteAreaForm.getDistance())
                .active(commuteAreaForm.isActive())
                .address(address)
            .build();
        commuteAreaRepository.save(commuteArea);
        return commuteArea;
    }

    private Address buildAddress(CommuteAreaForm commuteAreaForm) {
        return Address.builder()
                .city(commuteAreaForm.getCity())
                .roadName(commuteAreaForm.getRoadName())
                .detailAddress(commuteAreaForm.getDetailAddress())
                .siGunGu(commuteAreaForm.getSiGunGu())
                .dong(commuteAreaForm.getDong())
                .buildingName(commuteAreaForm.getBuildingName())
                .buildingNumber(commuteAreaForm.getBuildingNumber())
                .zoneCode(commuteAreaForm.getZoneCode())
                .latitude(commuteAreaForm.getLatitude())
                .longitude(commuteAreaForm.getLongitude())
                .build();
    }

}
