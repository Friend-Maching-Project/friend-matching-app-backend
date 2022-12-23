package com.example.potato.sic9.security.oauth;

import com.example.potato.sic9.common.AuthProvider;
import com.example.potato.sic9.common.Authority;
import com.example.potato.sic9.entity.User;
import com.example.potato.sic9.exception.OAuth2ProcessingException;
import com.example.potato.sic9.repository.UserRepository;
import com.example.potato.sic9.security.CustomUserDetails;
import com.example.potato.sic9.security.oauth.user.OAuth2UserInfo;
import com.example.potato.sic9.security.oauth.user.OAuth2UserInfoFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return process(userRequest, oAuth2User);
    }

    public OAuth2User process(OAuth2UserRequest oAuth2UserRequest,
                              OAuth2User oAuth2User) {
        AuthProvider authProvider = AuthProvider.valueOf(
                oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(authProvider, oAuth2User.getAttributes());
        if (userInfo.getOAuthId().isEmpty()) {
            throw new OAuth2ProcessingException("Email not found from OAuth2 provider");
        }
        Optional<User> userOptional = userRepository.findByOAuthId(userInfo.getOAuthId());
        User user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (authProvider != user.getAuthProvider()) {
                throw new OAuth2ProcessingException("Wrong Match Auth Provider");
            }
        } else {
            user = createUser(userInfo, authProvider);
        }
        return CustomUserDetails.create(user, oAuth2User.getAttributes());

    }

    private User createUser(OAuth2UserInfo userInfo, AuthProvider authProvider) {

        User user = User.builder()
                .OAuthId(userInfo.getOAuthId())
                .email(RandomStringUtils.randomAlphanumeric(19) + "@")
                .password(RandomStringUtils.randomAlphanumeric(20))
                .nickname(RandomStringUtils.randomAlphanumeric(20))
                .authority(Authority.ROLE_USER)
                .authProvider(authProvider)
                .build();
        return userRepository.save(user);
    }
}
