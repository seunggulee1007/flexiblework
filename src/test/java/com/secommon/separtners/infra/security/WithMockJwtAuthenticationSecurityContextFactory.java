package com.secommon.separtners.infra.security;

import com.secommon.separtners.modules.account.Account;
import com.secommon.separtners.modules.account.repository.AccountRepository;
import com.secommon.separtners.modules.account.AccountService;
import com.secommon.separtners.modules.account.enums.AccountRole;
import com.secommon.separtners.modules.account.form.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class WithMockJwtAuthenticationSecurityContextFactory implements WithSecurityContextFactory<WithMockJwtAuthentication> {

    private final AccountService accountService;

    private final AccountRepository accountRepository;

    @Override
    public SecurityContext createSecurityContext ( WithMockJwtAuthentication annotation ) {
        Account account = accountRepository.findByEmail( annotation.email() ).orElseGet( Account::new );
        if( account.getId() == null ) {
            SignUpForm signUpForm = SignUpForm.builder()
                    .email( annotation.email() )
                    .password( annotation.password() )
                    .userName( annotation.userName() )
                    .admin( annotation.admin() )
                    .build();
            account = accountService.processNewAccount( signUpForm );
        }
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        JwtAuthenticationToken authentication =
                new JwtAuthenticationToken(
                        new JwtAuthentication( account.getId(), account.getEmail() ), annotation.password(),
                        authorities( account.getRoles() )
                );
        context.setAuthentication( authentication );
        return context;
    }

    private Collection<? extends GrantedAuthority> authorities ( Set<AccountRole> role ) {
        return role.stream().map( r -> new SimpleGrantedAuthority( "ROLE_" + r.name() ) ).collect( Collectors.toSet() );
    }

}
