package com.lemon.supershop.swp391fa25evdm.configuration;

import com.lemon.supershop.swp391fa25evdm.authentication.service.AuthenService;
import com.lemon.supershop.swp391fa25evdm.authentication.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/oauth2/**", "/error").permitAll()
                        .anyRequest().authenticated()
                )
//                .oauth2Login(oauth -> oauth
//                        .loginPage("/login")
//                        .userInfoEndpoint(userInfo ->
//                                userInfo.userService(customOAuth2UserService) //  LIÊN KẾT SERVICE TẠI ĐÂY
//                        )
//                        .defaultSuccessUrl("/auth/google/success", true)  //  redirect để FE lấy JWT
//                        .failureUrl("/login?error=true")
                .oauth2Login(oauth -> oauth
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(customOAuth2UserService)
                        )
                        .defaultSuccessUrl("/auth/google/success", true)
                );

        return http.build();
    }
}
