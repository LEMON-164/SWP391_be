package com.lemon.supershop.swp391fa25evdm.user.repository;

import com.lemon.supershop.swp391fa25evdm.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
