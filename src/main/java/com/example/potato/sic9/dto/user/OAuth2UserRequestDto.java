package com.example.potato.sic9.dto.user;

import java.time.LocalDate;
import lombok.Data;

@Data
public class OAuth2UserRequestDto {
    private LocalDate birth;
    private String major;
    private String sex;
    private String nickname;
}
