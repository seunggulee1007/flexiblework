package com.secommon.separtners.modules.commute;


import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class CommuteFormValidator implements Validator {

    private final CommuteService commuteService;

    @Override
    public boolean supports ( Class<?> clazz ) {
        return clazz.isAssignableFrom( CommuteForm.class);
    }

    @Override
    public void validate ( @NonNull Object target, @NonNull Errors errors ) {
        CommuteForm commuteForm = (CommuteForm ) target;
        CommuteSearchForm commuteSearchForm = CommuteSearchForm.builder().accountId( commuteForm.getAccountId() ).build();
        CommuteDto commuteDto = commuteService.getMyCommute( commuteSearchForm );
        if(commuteForm.isOnOffFlag() && commuteDto.getOnWorkDate() != null) {
            errors.rejectValue( "onWorkDate", "wrong.value", "이미 출근 등록이 되어 있습니다." );
        } else if(!commuteForm.isOnOffFlag() && commuteDto.getOnWorkDate() == null ) {
            errors.rejectValue( "offWorkDate", "wrong.value", "출근 등록을 먼저 진행해 주세요." );
        }

    }

}
