package com.secommon.separtners.infra.security;

import static com.google.common.base.Preconditions.checkNotNull;

public class JwtAuthentication {

    public final Long accountId;
    public final String email;
    public JwtAuthentication(Long accountId, String email) {
        checkNotNull(accountId, "accountId must be provided");
        checkNotNull(email, "email must be provided"  );
        this.accountId = accountId;
        this.email = email;
    }

}