package com.example.potato.sic9.controller;

import com.example.potato.sic9.dto.TokenDto;
import com.example.potato.sic9.dto.auth.LoginRequestDto;
import com.example.potato.sic9.dto.auth.SignUpRequestDto;
import com.example.potato.sic9.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody SignUpRequestDto requestDto) throws Exception {
        authService.signUp(requestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
}
