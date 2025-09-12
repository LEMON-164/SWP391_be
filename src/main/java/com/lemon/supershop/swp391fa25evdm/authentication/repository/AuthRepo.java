package com.lemon.supershop.swp391fa25evdm.authentication.repository;

import com.lemon.supershop.swp391fa25evdm.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepo extends JpaRepository<User, Long> {
}
