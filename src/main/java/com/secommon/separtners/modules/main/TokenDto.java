package com.secommon.separtners.modules.main;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class TokenDto {

    private String accessToken;

    private String refreshToken;

    public TokenDto(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
