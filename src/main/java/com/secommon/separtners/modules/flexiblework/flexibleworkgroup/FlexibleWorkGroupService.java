package com.secommon.separtners.modules.flexiblework.flexibleworkgroup;

import com.secommon.separtners.infra.commons.BaseServiceAnnotation;
import com.secommon.separtners.modules.account.Account;
import com.secommon.separtners.modules.account.repository.AccountRepository;
import com.secommon.separtners.modules.company.employee.Employee;
import com.secommon.separtners.modules.company.employee.repository.EmployeeRepository;
import com.secommon.separtners.modules.flexiblework.flexiblework.FlexibleWork;
import com.secommon.separtners.modules.flexiblework.flexiblework.repository.FlexibleWorkRepository;
import com.secommon.separtners.modules.flexiblework.flexibleworkgroup.form.FlexibleWorkGroupForm;
import com.secommon.separtners.modules.flexiblework.flexibleworkgroup.repository.FlexibleWorkGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@BaseServiceAnnotation
@RequiredArgsConstructor
public class FlexibleWorkGroupService {

    private final FlexibleWorkGroupRepository flexibleWorkGroupRepository;
    private final FlexibleWorkRepository flexibleWorkRepository;
    private final AccountRepository accountRepository;

    /**
     * 유연근무 그룹 저장 로직
     * @param flexibleWorkGroupForm : 그룹 폼
     */
    public Long saveFlexibleWorkGroup( FlexibleWorkGroupForm flexibleWorkGroupForm) {
        Long flexibleWorkGroupId = flexibleWorkGroupForm.getFlexibleWorkGroupId();
        FlexibleWork flexibleWork = flexibleWorkRepository.findById( flexibleWorkGroupForm.getFlexibleWorkId() ).orElseThrow();
        if (flexibleWorkGroupId != null ) {
            changeFlexibleWorkGroup( flexibleWorkGroupForm, flexibleWorkGroupId, flexibleWork );
        } else {
            flexibleWorkGroupId = saveNewFlexibleWorkGroup( flexibleWorkGroupForm, flexibleWork );
        }
        return flexibleWorkGroupId;
    }

    /**
     * 유연근무 그룹 수정
     * @param flexibleWorkGroupForm : 변경 폼
     * @param flexibleWorkGroupId : /0
     * @param flexibleWork : 화면에서 가져온 유연근무 유형
     */
    private void changeFlexibleWorkGroup ( FlexibleWorkGroupForm flexibleWorkGroupForm, Long flexibleWorkGroupId, FlexibleWork flexibleWork ) {
        FlexibleWorkGroup flexibleWorkGroup = flexibleWorkGroupRepository.findById( flexibleWorkGroupId ).orElseThrow();
        removeDeletedEmployee( flexibleWorkGroupForm, flexibleWorkGroup );
        flexibleWorkGroup.updateFlexibleWorkGroup( flexibleWork, flexibleWorkGroupForm );
        for ( Long accountId: flexibleWorkGroupForm.getAccountIdList() ) {
            Account account = accountRepository.findById(accountId).orElseThrow();
            flexibleWorkGroup.addAccount(account);
        }
    }

    /**
     * 화면에서 삭제된 사원 데이터베이스에서 삭제
     * @param flexibleWorkGroupForm : 화면 전달 값
     * @param flexibleWorkGroup : 데이터베이스 값
     */
    private void removeDeletedEmployee ( FlexibleWorkGroupForm flexibleWorkGroupForm, FlexibleWorkGroup flexibleWorkGroup ) {
        List<Long> savedAccountIds = flexibleWorkGroup.getAccountList().stream().map( Account::getId ).collect( Collectors.toList());
        List<Long> compareList = savedAccountIds.stream().filter( compare ->
                flexibleWorkGroupForm.getAccountIdList().stream().noneMatch( Predicate.isEqual( compare ) )).collect( Collectors.toList());
        for ( Long accountId: compareList ) {
            Account account = accountRepository.findById(accountId).orElseThrow();
            account.removeWorkGroup();
        }
    }

    /**
     * 신규 그룹 등록
     * @param flexibleWorkGroupForm : 그룹 폼
     * @param flexibleWork : 연결될 유연근무유형
     */
    private Long saveNewFlexibleWorkGroup ( FlexibleWorkGroupForm flexibleWorkGroupForm, FlexibleWork flexibleWork ) {
        FlexibleWorkGroup flexibleWorkGroup = FlexibleWorkGroup.builder()
                .flexibleWorkGroupName( flexibleWorkGroupForm.getFlexibleWorkGroupName() )
                .flexibleWork( flexibleWork )
                .build();
        flexibleWorkGroupRepository.save( flexibleWorkGroup );
        for ( Long accountId: flexibleWorkGroupForm.getAccountIdList() ) {
            Account account = accountRepository.findById(accountId).orElseThrow();
            account.setWorkGroup( flexibleWorkGroup );
        }
        return flexibleWorkGroup.getId();
    }

}
