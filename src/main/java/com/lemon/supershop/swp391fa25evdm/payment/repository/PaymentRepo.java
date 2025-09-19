package com.lemon.supershop.swp391fa25evdm.payment.repository;

import com.lemon.supershop.swp391fa25evdm.payment.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepo extends JpaRepository<Payment, Integer> {
    List<Payment> findByUserId(int id);
    @Modifying
    @Query("UPDATE Payment p SET p.user = null WHERE p.user.id = :userId")
    void clearUserFromPayments(@Param("userId") Integer userId);

}
