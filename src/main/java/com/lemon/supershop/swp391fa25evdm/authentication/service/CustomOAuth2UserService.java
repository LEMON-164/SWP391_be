package com.lemon.supershop.swp391fa25evdm.authentication.service;

import com.lemon.supershop.swp391fa25evdm.authentication.model.dto.LoginRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private AuthenService authenService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oauthUser = delegate.loadUser(userRequest);

        // Gọi service login với Google
        LoginRes loginResult = authenService.loginWithGoogle(oauthUser);

        // Lưu token vào security context nếu cần -> hoặc gửi qua redirect URL
        // Bạn có thể lưu tạm vào attributes để frontend lấy
        oauthUser.getAttributes().put("jwt", loginResult.getToken());
        oauthUser.getAttributes().put("refreshToken", loginResult.getRefreshToken());

        return oauthUser;
    }
}
