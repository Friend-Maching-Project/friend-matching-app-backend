package com.example.potato.sic9.dto.auth;

import com.example.potato.sic9.entity.RefreshToken;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenDto {
    private String refreshToken;
    private Long tokenExpiresIn;

    public static RefreshTokenDto from(RefreshToken refreshToken) {
        return new RefreshTokenDto(refreshToken.getRefreshToken(), refreshToken.getTokenExpiresIn());
    }
}
