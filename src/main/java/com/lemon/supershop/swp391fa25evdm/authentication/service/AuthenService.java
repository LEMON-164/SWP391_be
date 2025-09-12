package com.lemon.supershop.swp391fa25evdm.authentication.service;

import com.lemon.supershop.swp391fa25evdm.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenService {

    @Autowired
    private UserService userService;
}
