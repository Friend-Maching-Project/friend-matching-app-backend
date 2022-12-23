package com.example.potato.sic9.security.jwt;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component

// 인증이 되지 않은 유저가 요청을 했을 때 동작
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // 유효한 자격증명을 제공하지 않고 접든하려 할 때 401 Error
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
