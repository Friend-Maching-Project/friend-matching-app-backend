package com.example.potato.sic9.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestDto {
    private String email;
    private String exPassword;
    private String newPassword;
}
