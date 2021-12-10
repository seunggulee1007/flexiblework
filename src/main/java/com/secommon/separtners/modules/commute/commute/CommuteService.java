package com.secommon.separtners.modules.commute.commute;

import com.secommon.separtners.infra.advice.exceptions.NoMemberException;
import com.secommon.separtners.infra.commons.BaseServiceAnnotation;
import com.secommon.separtners.infra.security.JwtAuthentication;
import com.secommon.separtners.modules.account.Account;
import com.secommon.separtners.modules.account.repository.AccountRepository;
import com.secommon.separtners.modules.commute.commute.form.CommuteForm;
import com.secommon.separtners.modules.commute.commute.form.CommuteSearchForm;
import com.secommon.separtners.modules.commute.commute.repository.CommuteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@BaseServiceAnnotation
@RequiredArgsConstructor
public class CommuteService {

    private final CommuteRepository commuteRepository;
    private final AccountRepository  accountRepository;

    @Transactional(readOnly = true)
    public CommuteDto getMyCommute ( CommuteSearchForm commuteSearchForm ) {
        return commuteRepository.findByAccountIdAndWorkedDateBetween( commuteSearchForm ).orElseGet( CommuteDto::new );
    }

    public CommuteDto doCommute(CommuteForm commuteForm, JwtAuthentication authentication ) {
        Commute commute = commuteRepository.findByAccountIdAndWorkedDateBetween(authentication.accountId).orElseGet( Commute::new );
        commute.setWorkDate(commuteForm.isOnOffFlag());
        if(commute.getId() == null) {
            Account account = accountRepository.findById( authentication.accountId ).orElseThrow( NoMemberException::new);
            commute.setAccount( account );
            commuteRepository.save( commute );
        }
        return new CommuteDto(commute);

    }

}
