package com.example.potato.sic9.entity;

import com.example.potato.sic9.dto.auth.RefreshTokenDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "token")
@NoArgsConstructor
@Getter
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token_name")
    private String name;

    @Column(name = "token_refresh")
    @Setter
    private String refreshToken;

    @Column(name = "token_expire_time")
    @Setter
    private Long tokenExpiresIn;

    public RefreshToken(String name, String refreshToken, Long tokenExpiresIn) {
        this.name = name;
        this.refreshToken = refreshToken;
        this.tokenExpiresIn = tokenExpiresIn;
    }

    public static RefreshToken of(String name, RefreshTokenDto dto) {
        return new RefreshToken(name, dto.getRefreshToken(), dto.getTokenExpiresIn());
    }
}
