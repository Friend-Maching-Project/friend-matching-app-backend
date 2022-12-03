package com.example.potato.sic9.service;

import com.example.potato.sic9.dto.auth.RefreshTokenDto;
import com.example.potato.sic9.entity.RefreshToken;
import com.example.potato.sic9.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public void saveRefreshToken(String name, RefreshTokenDto dto) {
        if (refreshTokenRepository.findByName(name).isPresent()) {
            RefreshToken token = refreshTokenRepository.findByName(name).get();
            token.setRefreshToken(dto.getRefreshToken());
            token.setTokenExpiresIn(dto.getTokenExpiresIn());
            refreshTokenRepository.save(token);
        } else {
            refreshTokenRepository.save(RefreshToken.of(name, dto));
        }
    }

    public RefreshTokenDto getRefreshToken(String refreshToken) {
        return RefreshTokenDto.from(
                refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow()); // TODO : 예외 처리
    }
}
