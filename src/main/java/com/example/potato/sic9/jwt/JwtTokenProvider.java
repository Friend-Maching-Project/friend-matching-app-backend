package com.example.potato.sic9.jwt;

import com.example.potato.sic9.dto.auth.RefreshTokenDto;
import com.example.potato.sic9.dto.auth.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 10;
    //    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 10;  // TODO : 테스트 후 수정
    private final Key key;
    private final UserDetailsService userDetailsService;


    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // Token 생성
    public Map<String, Object> generateTokenDtoSet(Authentication authentication) {
        Map<String, Object> tokens = new HashMap<>();
        // 권한을 받음. Ex) authorities = ROLE_USER
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // 현재 시간
        long now = new Date().getTime();

        // Token 만료 시간
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiresIn = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);
        log.info(String.valueOf(accessTokenExpiresIn));
        log.info(String.valueOf(refreshTokenExpiresIn));

        // Token 생성
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY,
                        authorities) // Custom Claim 지정, Claims는 JWT의 body이고 JWT 생성자가 JWT를 받는이들이게 제시하기 바라는 정보를 포함
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(accessTokenExpiresIn) // 만료시간
                .signWith(key, SignatureAlgorithm.HS512) // sign key 지정
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(refreshTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        // TokenDto에 생성한 Token의 정보를 넣는다
        TokenDto accessTokenDto = TokenDto.builder()
                .grantType(BEARER_TYPE)
                .token(accessToken)
                .tokenExpiresIn(accessTokenExpiresIn.getTime())
                .build();

        RefreshTokenDto refreshTokenDto = RefreshTokenDto.builder()
                .refreshToken(refreshToken)
                .tokenExpiresIn(refreshTokenExpiresIn.getTime())
                .build();

        tokens.put("accessToken", accessTokenDto);
        tokens.put("refreshToken", refreshTokenDto);
        return tokens;
    }

    public RefreshTokenDto reGenerateRefreshTokenDto(String userName) {
        long now = new Date().getTime();
        Date refreshTokenExpiresIn = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);
        String refreshToken = Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(refreshTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return RefreshTokenDto.builder()
                .refreshToken(refreshToken)
                .tokenExpiresIn(refreshTokenExpiresIn.getTime())
                .build();
    }

    public TokenDto generateAccessTokenDto(String userName) {

//        String authorities = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","));
        long now = new Date().getTime();
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

        String accessToken = Jwts.builder()
                .setSubject(userName)
                .claim(AUTHORITIES_KEY,
                        "ROLE_USER") // Custom Claim 지정, Claims는 JWT의 body이고 JWT 생성자가 JWT를 받는이들이게 제시하기 바라는 정보를 포함
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(accessTokenExpiresIn) // 만료시간
                .signWith(key, SignatureAlgorithm.HS512) // sign key 지정
                .compact();

        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .token(accessToken)
                .tokenExpiresIn(accessTokenExpiresIn.getTime())
                .build();
    }

    // Token을 받았을 때 Token의 인증을 꺼내는 메소드
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

//        Collection<? extends GrantedAuthority> authorities =
//                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
//                        .map(SimpleGrantedAuthority::new).toList();

        String username = getUserName(accessToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Token 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public String getUserName(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts
                    .parserBuilder() // JwtParseBuilder 인스턴스 생성
                    .setSigningKey(key) // JWT 서명 검증을 위한 키 설정
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }


}
