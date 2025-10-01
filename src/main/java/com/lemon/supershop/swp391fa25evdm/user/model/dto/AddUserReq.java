package com.lemon.supershop.swp391fa25evdm.user.model.dto;

public class AddUserReq {
    private String username;
    private String phone;
    private String email;
    private String password;
    private int roleId;
    private int dealerId;

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

    public int getRoleId() {
        return roleId;
    }

    public int getDealerId() {
        return dealerId;
    }
}
