package com.secommon.separtners.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public final class Jwt {

    private final String issuer;

    private final Algorithm algorithm;

    private final JWTVerifier jwtVerifier;

    public Jwt(String issuer, String secretKey) {
        this.issuer = issuer;
        this.algorithm = Algorithm.HMAC512( secretKey );
        this.jwtVerifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build();
    }

    public String createEmailToken ( Claims claims ) {
        long emailValidateSecond = 1000L * 60 * 60 * 24 * 7;
        return createToken( claims, emailValidateSecond );
    }
    public String createAccessToken( Claims claims ) {
        // 5분 동안만 토큰 유효
        long tokenValidationSecond = 1000L * 60 * 5;
        return createToken( claims, tokenValidationSecond );
    }

    public String createRefreshToken(Claims claims) {
        // 30분 동안 유효한 리프레시 토큰
        long refreshTokenValidationSecond = 1000L * 60 * 30;
        return createToken( claims, refreshTokenValidationSecond );
    }

    public String createToken( Claims claims, long expireTime) {
        Date now = new Date();
        JWTCreator.Builder builder = com.auth0.jwt.JWT.create();
        builder.withIssuer(issuer);
        builder.withIssuedAt(now);
        builder.withExpiresAt(new Date(now.getTime() + expireTime));
        builder.withClaim(JwtInfo.ACCOUNT_ID.name(), claims.accountId);
        builder.withClaim(JwtInfo.EMAIL.name(), claims.email);
        builder.withArrayClaim(JwtInfo.ROLES.name(), claims.roles);
        return builder.sign(algorithm);
    }



    public Claims verify(String token) throws JWTVerificationException {
        return new Claims(jwtVerifier.verify(token));
    }

    public boolean validateToken ( String token ) {
        return verify( token ).accountId != null;
    }

    @Data
    public static class Claims {
        Long accountId;
        String email;
        String[] roles;
        Date iat;
        Date exp;

        private Claims() {/*empty*/}

        Claims( DecodedJWT decodedJWT) {
            Claim accountIdClaim = decodedJWT.getClaim(JwtInfo.ACCOUNT_ID.name());
            if (!accountIdClaim.isNull()) {
                this.accountId = accountIdClaim.asLong();
            }
            Claim emailClaim = decodedJWT.getClaim(JwtInfo.EMAIL.name() );
            if (!emailClaim.isNull()) {
                this.email = emailClaim.asString();
            }
            Claim rolesClaim = decodedJWT.getClaim(JwtInfo.ROLES.name() );
            if (!rolesClaim.isNull()) {
                this.roles = rolesClaim.asArray( String.class );
            }
            this.iat = decodedJWT.getIssuedAt();
            this.exp = decodedJWT.getExpiresAt();
        }

        public static Claims of(long userKey, String name, String[] roles) {
            Claims claims = new Claims();
            claims.accountId = userKey;
            claims.email = name;
            claims.roles = roles;
            return claims;
        }

    }

}