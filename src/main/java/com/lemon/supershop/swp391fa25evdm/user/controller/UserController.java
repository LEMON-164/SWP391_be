package com.lemon.supershop.swp391fa25evdm.user.controller;

import com.lemon.supershop.swp391fa25evdm.authentication.model.dto.RegisterReq;
import com.lemon.supershop.swp391fa25evdm.authentication.service.AuthenService;
import com.lemon.supershop.swp391fa25evdm.user.model.dto.AddUserReq;
import com.lemon.supershop.swp391fa25evdm.user.model.dto.UserReq;
import com.lemon.supershop.swp391fa25evdm.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody AddUserReq dto) {
        userService.addUser(dto);
        return ResponseEntity.ok("User registered successfully");
    }

    @PutMapping("profile/{id}")
    public ResponseEntity<String> updateProfile(@PathVariable("id") int id, @RequestBody UserReq dto) throws Exception {
        userService.updateProfile(id, dto);
        return ResponseEntity.ok("User Updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id) {
        userService.removeUser(id);
        return ResponseEntity.ok("User Removed successfully");
    }
}
