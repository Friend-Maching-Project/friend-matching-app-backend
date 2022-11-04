package com.example.potato.sic9.config;

import com.example.potato.sic9.jwt.JwtTokenFilter;
import com.example.potato.sic9.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
// TokenProvider와 JwtFilter를 SecurityConfig에 적용할 때 사용
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    // TokenProvider를 주입받아서 JwtFilter를 통해 SpringConfig 안에 필터를 등록하게 되고, 스프링 시큐리티 전반적인 필터에 적용
    public void configure(HttpSecurity http) throws Exception {
        JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider);
        // addFilterBefore : 지정된 필터 앞에 커스텀 필터를 추가
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
