package com.lemon.supershop.swp391fa25evdm.user.repository;

import com.lemon.supershop.swp391fa25evdm.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
    List<User> findByUsernameContainingIgnoreCase(String username);
    Optional<User> findById(int id);
    Optional<User> findByIsBlackTrue();
    Optional<User> findByIsBlackFalse();

}
