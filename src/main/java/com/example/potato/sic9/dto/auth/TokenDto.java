package com.example.potato.sic9.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
// Token의 값을 헤더에서 뽑거나 헤더에서 삽입할 때 쓰는 DTO
public class TokenDto {
    private String grantType;
    private String token;
    private Long tokenExpiresIn;
}
