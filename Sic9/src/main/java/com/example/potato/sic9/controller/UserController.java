package com.example.potato.sic9.controller;

import com.example.potato.sic9.annotation.AuthUser;
import com.example.potato.sic9.dto.ChangePasswordRequestDto;
import com.example.potato.sic9.dto.user.UserRequestDto;
import com.example.potato.sic9.dto.user.UserResponseDto;
import com.example.potato.sic9.entity.User;
import com.example.potato.sic9.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyUserInfo(@AuthUser User user) {
        return ResponseEntity.ok(UserResponseDto.of(user));
    }

    @PostMapping("/nickname")
    public ResponseEntity<UserResponseDto> setUserNickname(@RequestBody UserRequestDto req) {
        return ResponseEntity.ok(userService.changeUserNickname(req.getEmail(), req.getNickname()));
    }

    @PostMapping("/password")
    public ResponseEntity<UserResponseDto> setUserPassword(@RequestBody ChangePasswordRequestDto request) {
        return ResponseEntity.ok(userService.changeMemberPassword(request.getExPassword(), request.getNewPassword()));
    }

}
