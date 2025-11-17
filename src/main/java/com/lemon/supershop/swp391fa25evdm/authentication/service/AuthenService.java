package com.lemon.supershop.swp391fa25evdm.authentication.service;

import com.lemon.supershop.swp391fa25evdm.authentication.model.dto.*;
import com.lemon.supershop.swp391fa25evdm.dealer.model.entity.Dealer;
import com.lemon.supershop.swp391fa25evdm.dealer.repository.DealerRepo;
import com.lemon.supershop.swp391fa25evdm.refra.JwtUtil;
import com.lemon.supershop.swp391fa25evdm.role.model.entity.Role;
import com.lemon.supershop.swp391fa25evdm.role.repository.RoleRepo;
import com.lemon.supershop.swp391fa25evdm.user.model.entity.User;
import com.lemon.supershop.swp391fa25evdm.user.model.enums.UserStatus;
import com.lemon.supershop.swp391fa25evdm.user.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AuthenService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private DealerRepo dealerRepo;

    @Autowired
    private JwtUtil jwtUtil;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", Pattern.CASE_INSENSITIVE);

    // Hợp lệ cho VN: 10-digit bắt đầu 03|05|07|08|09 OR old 11-digit 01(2|6|8|9)
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^(?:(?:03|05|07|08|09)\\d{8}|01(?:2|6|8|9)\\d{8})$");


    public LoginRes login(LoginReq dto) {
        Optional<User> userOpt = Optional.empty();
        if (dto.getIdentifier() != null){
            if (EMAIL_PATTERN.matcher(dto.getIdentifier()).matches()){
                userOpt = userRepo.findByEmail(dto.getIdentifier());
            } else {
                userOpt = userRepo.findByUsername(dto.getIdentifier());
            }
        }

        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();

        if (user.getStatus() == UserStatus.INACTIVE) {
            throw new RuntimeException("Account inactive");
        }

        if (!user.getPassword().equals(dto.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        LoginRes response = new LoginRes(token, refreshToken, user.getUsername(), user.getRole().getName());

        // Thêm thông tin user và dealer nếu có
        response.setUserId(user.getId());
        if (user.getDealer() != null) {
            response.setDealerId(user.getDealer().getId());
            response.setDealerName(user.getDealer().getName());
            response.setDealerAddress(user.getDealer().getAddress());
        }

        return response;
    }

    public LoginRes loginWithGoogle(OAuth2User oauthUser) {

        // Google trả về dữ liệu trong oauthUser
        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new RuntimeException("Phải cung cấp email đúng form");
        }



        // Tìm user trong DB
        Optional<User> userOpt = userRepo.findByEmail(email);

        User user;

        if (userOpt.isPresent()) {
            user = userOpt.get(); // user đã có trong hệ thống
            if (user.getStatus() == UserStatus.INACTIVE) {
                throw new RuntimeException("Account inactive");
            }
        } else {
            // Tạo user mới
            user = new User();
            user.setEmail(email);
            user.setUsername(name);
            user.setPassword("");   // Google không trả → để trống
            user.setPhone("");      // Google không trả phone
            user.setAddress("");    // Google không trả address
            user.setStatus(UserStatus.ACTIVE);

            // Gán role mặc định (USER)
            Role defaultRole = roleRepo.findByNameContainingIgnoreCase("USER")
                    .orElseThrow(() -> new RuntimeException("Default role USER not found"));
            user.setRole(defaultRole);

            user = userRepo.save(user); // lưu user mới
        }

        // Tạo JWT + Refresh token
        String token = jwtUtil.generateToken(user.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        LoginRes res = new LoginRes(token, refreshToken, user.getUsername(), user.getRole().getName());
        res.setUserId(user.getId());

        if (user.getDealer() != null) {
            res.setDealerId(user.getDealer().getId());
            res.setDealerName(user.getDealer().getName());
            res.setDealerAddress(user.getDealer().getAddress());
        }

        return res;
    }

    public void register(RegisterReq dto) {
        User user = new User();
        user.setId(0);
        User newUser = converttoEntity(user, dto);
        if (newUser != null) {
            userRepo.save(newUser);
        }
    }

    public void changePassword(int id, ChangePassReq dto){
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            String oldPass = dto.getOldPass();
            String newPass = dto.getNewPass();
            String confirmPass = dto.getConfirmPass();

            if (oldPass == null || newPass == null || confirmPass == null) {
                throw new RuntimeException("Password fields must not be null");
            }

            if (!oldPass.equals(user.get().getPassword())) {
                throw new RuntimeException("Old password does not match");
            }

            if (newPass.equals(user.get().getPassword())) {
                throw new RuntimeException("New password must be different from old password");
            }

            if (!newPass.equals(confirmPass)) {
                throw new RuntimeException("Confirm password does not match new password");
            }

            user.get().setPassword(newPass);
            userRepo.save(user.get());
        }
    }

    public User converttoEntity(User user, RegisterReq dto){
        if (user != null) {
            if (dto.getRoleName() != null){
                Optional<Role> role = roleRepo.findByNameContainingIgnoreCase(dto.getRoleName());
                if (role.isPresent()) {
                    user.setRole(role.get());
                    role.get().addUser(user);
                }
            }
            if (dto.getPhone() != null && PHONE_PATTERN.matcher(dto.getPhone()).matches()){
                user.setPhone(dto.getPhone());
            }
            if (dto.getEmail() != null && EMAIL_PATTERN.matcher(dto.getEmail()).matches()){
                if (userRepo.existsByEmail(dto.getEmail())){
                    throw new RuntimeException("EMAIL_DUPLICATE");
                } else {
                    user.setEmail(dto.getEmail());
                }
            }
            if (dto.getPassword().equals(dto.getConfirmPassword())){
                user.setPassword(dto.getPassword());
            }
            if (dto.getUsername() != null) {
                user.setUsername(dto.getUsername());
            }
            if (dto.getAddress() != null){
                user.setAddress(dto.getAddress());
            }
            if (dto.getDealerId() != null && dto.getDealerId() > 0) {
                Optional<Dealer> dealer = dealerRepo.findById(dto.getDealerId());
                if (dealer.isPresent()) {
                    user.setDealer(dealer.get());
                }
            }
            user.setStatus(UserStatus.ACTIVE);
            return user;
        }
        return null;
    }
}
