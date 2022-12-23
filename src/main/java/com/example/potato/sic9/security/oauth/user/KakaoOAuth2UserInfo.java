package com.example.potato.sic9.security.oauth.user;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getOAuthId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getEmail() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");

        if (response == null) {
            return null;
        }
        return (String) response.get("email");
    }
}
