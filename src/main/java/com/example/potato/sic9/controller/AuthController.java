package com.example.potato.sic9.controller;

import com.example.potato.sic9.common.ExpireTime;
import com.example.potato.sic9.dto.auth.AccessTokenDto;
import com.example.potato.sic9.dto.auth.LoginRequestDto;
import com.example.potato.sic9.dto.auth.RefreshTokenDto;
import com.example.potato.sic9.dto.auth.SignUpRequestDto;
import com.example.potato.sic9.security.jwt.JwtTokenProvider;
import com.example.potato.sic9.service.AuthService;
import com.example.potato.sic9.service.RefreshTokenService;
import com.example.potato.sic9.util.CookieUtil;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody SignUpRequestDto requestDto) throws Exception {
        authService.signUp(requestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/email-double-check")
    public ResponseEntity<Void> emailDoubleCheck(@RequestBody SignUpRequestDto requestDto) {
        if (authService.emailDoubleCheck(requestDto)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("nickname-double-check")
    public ResponseEntity<Void> nicknameDoubleCheck(@RequestParam String nickname) {
        if (authService.nicknameDoubleCheck(nickname)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AccessTokenDto> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        Map<String, Object> tokens = authService.login(requestDto);
        AccessTokenDto accessToken = (AccessTokenDto) tokens.get("accessToken");
        RefreshTokenDto refreshTokenDto = (RefreshTokenDto) tokens.get("refreshToken");
        String name = jwtTokenProvider.getUserName(accessToken.getToken());
        refreshTokenService.saveRefreshToken(name, refreshTokenDto);
        String refreshToken = refreshTokenDto.getRefreshToken();
        CookieUtil.addCookie(response, "refreshToken", refreshToken,
                (int) ExpireTime.REFRESH_COOKIE_EXPIRE_TIME.getTime());

        return ResponseEntity.ok(accessToken);
    }

    @GetMapping("/refresh")
    public ResponseEntity<AccessTokenDto> refreshToken(
            @CookieValue(value = "refreshToken", required = false) String cookieRefreshToken,
            HttpServletResponse response) {
        String username = null;
        if (cookieRefreshToken == null) {
            // TODO : 예외처리
            // RFT 재발급
        }
        String refreshToken = refreshTokenService.getRefreshToken(cookieRefreshToken).getRefreshToken();
        if (jwtTokenProvider.validateToken(refreshToken)) {
            Long expiresIn = refreshTokenService.getRefreshToken(refreshToken).getTokenExpiresIn();
            long now = new Date().getTime();
            username = jwtTokenProvider.getUserName(refreshToken);
            if (expiresIn - now <= 1000 * 60 * 60) { // ex) RFT 5시간이면 1시간 남았을 때 RFT 토큰 재발급, 1000 = 1s
                // refresh 토큰 재발급
                RefreshTokenDto newRefreshTokenDto = jwtTokenProvider.reGenerateRefreshTokenDto(username);
                refreshToken = newRefreshTokenDto.getRefreshToken();
                if (jwtTokenProvider.validateToken(refreshToken)) {
                    refreshTokenService.saveRefreshToken(username, newRefreshTokenDto);
                    CookieUtil.addCookie(response, "refreshToken", refreshToken,
                            (int) ExpireTime.REFRESH_COOKIE_EXPIRE_TIME.getTime());
                } else {
                    // TODO : 예외 처리
                }
            }

        }
        return ResponseEntity.ok(jwtTokenProvider.generateAccessTokenDto(username));
    }
}
