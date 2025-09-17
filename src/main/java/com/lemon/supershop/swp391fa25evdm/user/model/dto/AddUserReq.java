package com.lemon.supershop.swp391fa25evdm.user.model.dto;

import com.lemon.supershop.swp391fa25evdm.role.model.dto.RoleDto;
import com.lemon.supershop.swp391fa25evdm.role.model.entity.Role;

public class AddUserReq {
    private String username;
    private String phone;
    private String email;
    private String password;
    private String role;

    public AddUserReq() {
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
