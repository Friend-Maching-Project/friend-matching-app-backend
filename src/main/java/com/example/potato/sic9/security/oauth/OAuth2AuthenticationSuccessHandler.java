package com.example.potato.sic9.security.oauth;

import com.example.potato.sic9.common.ExpireTime;
import com.example.potato.sic9.dto.auth.AccessTokenDto;
import com.example.potato.sic9.dto.auth.RefreshTokenDto;
import com.example.potato.sic9.exception.BadRequestException;
import com.example.potato.sic9.security.jwt.JwtTokenProvider;
import com.example.potato.sic9.service.RefreshTokenService;
import com.example.potato.sic9.util.CookieUtil;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${app.oauth2.authorizedRedirectUri}")
    private String redirectUri;
    private final JwtTokenProvider jwtTokenProvider;
    private final CookieAuthorizationRequestRepository authorizationRequestRepository;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        if (response.isCommitted()) {
            log.debug("Response has already been committed");
            return;
        }
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, "redirect_uri").map(Cookie::getValue);

        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new BadRequestException("redirect URIs are not matched");
        }
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        Map<String, Object> tokenDtoSet = jwtTokenProvider.generateTokenDtoSet(authentication);
        AccessTokenDto accessTokenDto = (AccessTokenDto) tokenDtoSet.get("accessToken");
        RefreshTokenDto refreshTokenDto = (RefreshTokenDto) tokenDtoSet.get("refreshToken");
        String accessToken = accessTokenDto.getToken();
        String refreshToken = refreshTokenDto.getRefreshToken();
        refreshTokenService.saveRefreshToken(authentication.getName(), refreshTokenDto);
        CookieUtil.addCookie(response, "refreshToken", refreshToken,
                (int) ExpireTime.REFRESH_COOKIE_EXPIRE_TIME.getTime());

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("accessToken", accessToken)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);
        URI authorizedUri = URI.create(redirectUri);

        return authorizedUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                && authorizedUri.getPort() == clientRedirectUri.getPort();
    }
}
