package com.example.potato.sic9.service;

import com.example.potato.sic9.config.SecurityUtil;
import com.example.potato.sic9.dto.user.OAuth2UserRequestDto;
import com.example.potato.sic9.dto.user.UserResponseDto;
import com.example.potato.sic9.entity.User;
import com.example.potato.sic9.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto changeUserNickname(String email, String nickname) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
        user.setNickname(nickname);
        return UserResponseDto.of(userRepository.save(user));
    }

    @Transactional
    public UserResponseDto changeMemberPassword(String exPassword, String newPassword) {
        User user = userRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
        if (!passwordEncoder.matches(exPassword, user.getPassword())) {
            throw new RuntimeException("비밀번호가 맞지 않습니다.");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        return UserResponseDto.of(userRepository.save(user));
    }

    @Transactional
    public UserResponseDto changeOAuthUserInfo(User user, OAuth2UserRequestDto oAuth2UserRequestDto) {
        User beforeUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("회원 정보가 없습니다."));
        beforeUser.setNickname(oAuth2UserRequestDto.getNickname());
        beforeUser.setBirth(oAuth2UserRequestDto.getBirth());
        beforeUser.setMajor(oAuth2UserRequestDto.getMajor());
        beforeUser.setSex(oAuth2UserRequestDto.getSex());
        return UserResponseDto.of(userRepository.save(beforeUser));
    }
}
