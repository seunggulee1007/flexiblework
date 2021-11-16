package com.secommon.separtners.infra.security;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockJwtAuthenticationSecurityContextFactory.class)
public @interface WithMockJwtAuthentication {

    long id () default 1L;

    String email () default "leesg107@naver.com";

    String userName () default "관리자";

    String password () default "qlalfqjsgh1!";

    boolean admin () default true;

}
